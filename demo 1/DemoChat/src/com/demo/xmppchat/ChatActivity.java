package com.demo.xmppchat;

import java.util.ArrayList;
import java.util.List;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import com.demo.xmppchat.util.ServerInfo;
import com.demo.xmppchat.util.TimeRender;
import com.demo.xmppchat.util.XmppTool;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ChatActivity extends Activity {

	private static final String TAG = ChatActivity.class.getSimpleName();

	private String FROM_USERNAME;
	private String TO_USERNAME;
	
	private MyAdapter adapter;
	private List<Msg> listMsg = new ArrayList<Msg>();
	private String pUSERID;
	private EditText msgText;
	private ProgressBar pb;

	public class Msg {
		String userid;
		String msg;
		String date;
		String from;

		public Msg(String userid, String msg, String date, String from) {
			this.userid = userid;
			this.msg = msg;
			this.date = date;
			this.from = from;
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.formclient);
		this.pUSERID = getIntent().getStringExtra("USERID");
		setUser();
		initView();

		ChatManager cm = XmppTool.getConnection().getChatManager();
		final Chat newchat = cm.createChat(
				FROM_USERNAME + ServerInfo.getName(), null);

		cm.addChatListener(new ChatManagerListener() {
			@Override
			public void chatCreated(Chat chat, boolean able) {
				chat.addMessageListener(new MessageListener() {
					@Override
					public void processMessage(Chat chat2, Message message) {

						if (message.getFrom().contains(FROM_USERNAME)) {
							String[] args = new String[] { FROM_USERNAME,
									message.getBody(), TimeRender.getDate(),
									"IN" };
							android.os.Message msg = handler.obtainMessage();
							msg.what = MSG_TEXT_RECEIVED_SUCCESS;
							msg.obj = args;
							msg.sendToTarget();
						} else {
						}
					}
				});
			}
		});

		// send message
		Button btsend = (Button) findViewById(R.id.formclient_btsend);
		btsend.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String msg = msgText.getText().toString();
				if (msg.length() > 0) {
					listMsg.add(new Msg(pUSERID, msg, TimeRender.getDate(),
							"OUT"));
					adapter.notifyDataSetChanged();
					try {
						Log.d(TAG, "-------->send msg: " + msg);
						newchat.sendMessage(msg);

					} catch (XMPPException e) {
						e.printStackTrace();
					}
				}
				msgText.setText("");
			}
		});
	}

	private void initView() {
		ListView listview = (ListView) findViewById(R.id.formclient_listview);
		listview.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
		this.adapter = new MyAdapter(this);
		listview.setAdapter(adapter);
		this.msgText = (EditText) findViewById(R.id.formclient_text);
		this.pb = (ProgressBar) findViewById(R.id.formclient_pb);
	}

	private static final int MSG_TEXT_RECEIVED_SUCCESS = 1;
	private static final int MSG_PROGRESSBAR_VISIBLE = 2;
	private static final int MSG_PROGRESSBAR_UPDATING = 3;
	private static final int MSG_PROGRESSBAR_GONE = 4;

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case MSG_TEXT_RECEIVED_SUCCESS:
				String[] args = (String[]) msg.obj;
				listMsg.add(new Msg(args[0], args[1], args[2], args[3]));
				adapter.notifyDataSetChanged();
				break;
			case MSG_PROGRESSBAR_VISIBLE:
				if (pb.getVisibility() == View.GONE) {
					pb.setMax(100);
					pb.setProgress(0);
					pb.setVisibility(View.VISIBLE);
				}
				break;
			case MSG_PROGRESSBAR_UPDATING:
				pb.setProgress(msg.arg1);
				break;
			case MSG_PROGRESSBAR_GONE:
				pb.setVisibility(View.GONE);
				break;

			default:
				break;
			}
		};
	};

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		XmppTool.closeConnection();
		System.exit(0);
	}

	class MyAdapter extends BaseAdapter {

		private Context cxt;
		private LayoutInflater inflater;

		public MyAdapter(ChatActivity ChatActivity) {
			this.cxt = ChatActivity;
		}

		@Override
		public int getCount() {
			return listMsg.size();
		}

		@Override
		public Object getItem(int position) {
			return listMsg.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			this.inflater = (LayoutInflater) this.cxt
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			if (listMsg.get(position).from.equals("IN")) {
				convertView = this.inflater.inflate(
						R.layout.formclient_chat_in, null);
			} else {
				convertView = this.inflater.inflate(
						R.layout.formclient_chat_out, null);
			}
			TextView useridView = (TextView) convertView
					.findViewById(R.id.formclient_row_userid);
			TextView dateView = (TextView) convertView
					.findViewById(R.id.formclient_row_date);
			TextView msgView = (TextView) convertView
					.findViewById(R.id.formclient_row_msg);
			useridView.setText(listMsg.get(position).userid);
			dateView.setText(listMsg.get(position).date);
			msgView.setText(listMsg.get(position).msg);
			return convertView;
		}
	}
	
	public void setUser() {
		if (this.pUSERID == "test1" ) {
			this.FROM_USERNAME = "test1";
			this.TO_USERNAME = "test2";
		} else {
			this.FROM_USERNAME = "test2";
			this.TO_USERNAME = "test1";
		}
	}
}