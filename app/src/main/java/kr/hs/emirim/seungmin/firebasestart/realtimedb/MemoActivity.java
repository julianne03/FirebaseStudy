package kr.hs.emirim.seungmin.firebasestart.realtimedb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import kr.hs.emirim.seungmin.firebasestart.R;

public class MemoActivity extends AppCompatActivity implements View.OnClickListener, MemoViewListener{

    private ArrayList<MemoItem> memoItems = null;
    private MemoAdapter memoAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo);

        init();
        initView();
    }

    private void init() {
        memoItems = new ArrayList<>();
    }
    private void initView() {
        Button regbtn = findViewById(R.id.memobtn);
        regbtn.setOnClickListener(this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerView recyclerView = findViewById(R.id.memolist);
        memoAdapter = new MemoAdapter(this, memoItems, this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(memoAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.memobtn :
                regMemo();
                break;
        }
    }

    private void regMemo() {
        EditText titleedit = findViewById(R.id.memotitle);
        EditText contentedit = findViewById(R.id.memocontents);
        if(titleedit.getText().toString().length() == 0 ||
            contentedit.getText().toString().length() ==0 ) {
            Toast.makeText(this, "메모 제목 또는 내용이 입력되지 않았습니다. 입력 후 다시 시작해주세요.",
                    Toast.LENGTH_SHORT).show();

            return;

        }
        MemoItem item = new MemoItem();
        item.setTitle(titleedit.getText().toString());
        item.setMemocontents(contentedit.getText().toString());

        memoItems.add(item);
        memoAdapter.notifyDataSetChanged();

    }

    @Override
    public void onItemClick(int position, View view) {

    }
}