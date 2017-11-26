package org.hseury.easybrowser.model;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Handler;
import android.os.Message;
import java.nio.ByteBuffer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import org.hseury.easybrowser.tab.Tab;
import org.hseury.easybrowser.model.BrowserContract.History;

/**
 * @description: Created by hseury on 2017/11/22.
 */

public class DataController {
	private static final String LOGTAG = "DataController";
	// Message IDs
	private static final int HISTORY_UPDATE_VISITED = 100;
	private static final int HISTORY_UPDATE_TITLE = 101;
	private static final int QUERY_URL_IS_BOOKMARK = 200;
	private static final int TAB_LOAD_THUMBNAIL = 201;
	private static final int TAB_SAVE_THUMBNAIL = 202;
	private static final int TAB_DELETE_THUMBNAIL = 203;
	private static DataController sInstance;

	private Context mContext;
	private DataControllerHandler mDataHandler;
	private Handler mCbHandler; // To respond on the UI thread
	private ByteBuffer mBuffer; // to capture thumbnails

	private static class CallbackContainer {
		Object replyTo;
		Object[] args;
	}

	private static class DCMessage {
		int what;
		Object obj;
		Object replyTo;
		DCMessage(int w, Object o) {
			what = w;
			obj = o;
		}
	}

	/* package */ public static DataController getInstance(Context c) {
		if (sInstance == null) {
			sInstance = new DataController(c);
		}
		return sInstance;
	}

	private DataController(Context c) {
		mContext = c.getApplicationContext();
		mDataHandler = new DataControllerHandler();
		mDataHandler.start();
		//mCbHandler = new Handler() {
		//	@Override
		//	public void handleMessage(Message msg) {
		//		CallbackContainer cc = (CallbackContainer) msg.obj;
		//		switch (msg.what) {
		//			case QUERY_URL_IS_BOOKMARK: {
		//				OnQueryUrlIsBookmark cb = (OnQueryUrlIsBookmark) cc.replyTo;
		//				String url = (String) cc.args[0];
		//				boolean isBookmark = (Boolean) cc.args[1];
		//				cb.onQueryUrlIsBookmark(url, isBookmark);
		//				break;
		//			}
		//		}
		//	}
		//};
	}

	public void updateVisitedHistory(String url) {
		mDataHandler.sendMessage(HISTORY_UPDATE_VISITED, url);
	}

	// The standard Handler and Message classes don't allow the queue manipulation
	// we want (such as peeking). So we use our own queue.
	class DataControllerHandler extends Thread {
		private BlockingQueue<DCMessage> mMessageQueue
				= new LinkedBlockingQueue<DCMessage>();

		public DataControllerHandler() {
			super("DataControllerHandler");
		}

		@Override
		public void run() {
			setPriority(Thread.MIN_PRIORITY);
			while (true) {
				try {
					handleMessage(mMessageQueue.take());
				} catch (InterruptedException ex) {
					break;
				}
			}
		}

		void sendMessage(int what, Object obj) {
			DCMessage m = new DCMessage(what, obj);
			mMessageQueue.add(m);
		}

		void sendMessage(int what, Object obj, Object replyTo) {
			DCMessage m = new DCMessage(what, obj);
			m.replyTo = replyTo;
			mMessageQueue.add(m);
		}

		private void handleMessage(DCMessage msg) {
			switch (msg.what) {
				case HISTORY_UPDATE_VISITED:
					doUpdateVisitedHistory((String) msg.obj);
					break;
				default:
					break;
			}
		}
		private void doUpdateVisitedHistory(String url) {
			ContentResolver cr = mContext.getContentResolver();
			Cursor c = null;
			try {
				c = cr.query(
						History.CONTENT_URI, new String[] { History._ID, History.VISITS },
						History.URL + "=?", new String[] { url }, null);
				if (c.moveToFirst()) {
					ContentValues values = new ContentValues();
					values.put(BrowserContract.History.VISITS, c.getInt(1) + 1);
					values.put(History.DATE_LAST_VISITED, System.currentTimeMillis());
					cr.update(ContentUris.withAppendedId(History.CONTENT_URI, c.getLong(0)),
							values, null, null);
				} else {
					//TODO:
					//     keep our history table to a reasonable size
					//com.android.stockbrowser.platformsupport.Browser.truncateHistory(cr);
					ContentValues values = new ContentValues();
					values.put(History.URL, url);
					values.put(History.VISITS, 1);
					values.put(History.DATE_LAST_VISITED, System.currentTimeMillis());
					values.put(History.TITLE, url);
					values.put(History.DATE_CREATED, 0);
					values.put(History.USER_ENTERED, 0);
					cr.insert(History.CONTENT_URI, values);
				}
			} finally {
				if (c != null) c.close();
			}
		}
	}
}
