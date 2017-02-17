package bravest.ptt.ndscnoresponse;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "bravest_ptt";

    private Button mCorrectly;
    private Button mIncorrectly;
    private RecyclerView mRecyclerViewCorrect;
    private RecyclerView mRecyclerViewIncorrect;

    private ArrayList<String> mData4Correct;
    private ArrayList<String> mData4Incorrect;

    private MyAdapter mAdapter4Correct;
    private MyAdapter mAdapter4Incorrect;

    private long seed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerViewCorrect = (RecyclerView) findViewById(R.id.recyclerView_correct);
        mRecyclerViewIncorrect = (RecyclerView) findViewById(R.id.recyclerView_incorrect);

        mCorrectly = (Button) findViewById(R.id.use_correctly);
        mIncorrectly = (Button) findViewById(R.id.use_incorrectly);
        mCorrectly.setOnClickListener(this);
        mIncorrectly.setOnClickListener(this);

        //The key point
        mData4Correct = new ArrayList<>();

        mData4Correct.addAll(dataSource());
        mData4Incorrect = dataSource();

        mAdapter4Correct = new MyAdapter(this,mData4Correct);
        mAdapter4Incorrect = new MyAdapter(this,mData4Incorrect);

        initRecyclerViews();

    }

    //Mock data source
    private ArrayList<String> dataSource() {
        ArrayList<String> array = new ArrayList<>();
        for (int i = 0; i < 20; i ++) {
            array.add(String.valueOf(i + seed++));
        }
        return array;
    }

    private void initRecyclerViews() {
        //One LinearLayoutManager just can be used once
        mRecyclerViewCorrect.setLayoutManager( new LinearLayoutManager(this));
        mRecyclerViewCorrect.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        mRecyclerViewCorrect.setAdapter(mAdapter4Correct);

        mRecyclerViewIncorrect.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewIncorrect.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        mRecyclerViewIncorrect.setAdapter(mAdapter4Incorrect);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.use_correctly:
                mData4Correct.clear();
                mData4Correct.addAll(dataSource());
                Log.d(TAG, "onClick: use correctly data address : " + mData4Correct);
                mAdapter4Correct.notifyDataSetChanged();
                break;
            case R.id.use_incorrectly:
                mData4Incorrect = dataSource();
                Log.d(TAG, "onClick: use incorrectly data address : " + mData4Incorrect);
                mAdapter4Incorrect.notifyDataSetChanged();
                break;
            default:
                break;
        }
    }

    class MyHolder extends RecyclerView.ViewHolder {

        public TextView text;
        public MyHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.text);
        }
    }

    class MyAdapter extends RecyclerView.Adapter<MyHolder> {

        private ArrayList<String> mData;
        private Context mContext;
        public MyAdapter(Context context, ArrayList<String> data) {
            mContext = context;
            this.mData = data;
        }

        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            if (inflater == null) {
                return null;
            }
            return new MyHolder(inflater.inflate(R.layout.item_layout, null));
        }

        @Override
        public void onBindViewHolder(MyHolder holder, int position) {
            if (mData == null) {
                return;
            }
            Log.d(TAG, "onBindViewHolder: position = " + position);
            holder.text.setText(mData.get(position));
        }

        @Override
        public int getItemCount() {
            if (mData != null) {
                return mData.size();
            }
            return 0;
        }
    }
}
