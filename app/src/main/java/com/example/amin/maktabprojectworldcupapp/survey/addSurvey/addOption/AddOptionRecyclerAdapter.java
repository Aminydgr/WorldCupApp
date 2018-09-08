package com.example.amin.maktabprojectworldcupapp.survey.addSurvey.addOption;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.amin.maktabprojectworldcupapp.R;
import com.example.amin.maktabprojectworldcupapp.model.Option;

import java.util.List;

/**
 * Created by Amin on 8/17/2018.
 */

public class AddOptionRecyclerAdapter extends RecyclerView.Adapter<AddOptionRecyclerAdapter.AddOptionRecyclerHolder> {

    private Context context;
    private List<Option> optionList;

    public AddOptionRecyclerAdapter(Context context, List<Option> optionList) {
        this.context = context;
        this.optionList = optionList;
    }

    @Override
    public AddOptionRecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from ( context ).inflate ( R.layout.add_options_recycler_item, null );
        return new AddOptionRecyclerHolder ( view );
    }

    @Override
    public void onBindViewHolder(AddOptionRecyclerHolder holder, int position) {
        holder.bindOption(optionList.get ( position ));
    }

    @Override
    public int getItemCount() {
        return optionList.size ();
    }

    public class AddOptionRecyclerHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView optionTextTxtView;
        private Button deleteOptionBtn;

        private Option option;

        public AddOptionRecyclerHolder(View itemView) {
            super ( itemView );

            optionTextTxtView = (TextView) itemView.findViewById ( R.id.option_text_txtView );
            deleteOptionBtn = (Button) itemView.findViewById ( R.id.delete_option_btn );

            deleteOptionBtn.setOnClickListener ( this );
        }

        public void bindOption(Option option) {
            this.option = option;
            optionTextTxtView.setText ( option.getText () );
        }

        @Override
        public void onClick(View v) {
            optionList.remove ( option );
            notifyDataSetChanged ();
        }
    }
}
