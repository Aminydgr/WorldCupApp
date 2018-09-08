package com.example.amin.maktabprojectworldcupapp.survey;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.example.amin.maktabprojectworldcupapp.R;
import com.example.amin.maktabprojectworldcupapp.model.Option;

import java.util.List;

/**
 * Created by Amin on 8/30/2018.
 */

public class OptionAdapter extends RecyclerView.Adapter<OptionAdapter.OptionHolder> {

    private Context context;
    private List<Option> optionList;

    public OptionAdapter(Context context, List<Option> optionList) {
        this.context = context;
        this.optionList = optionList;
    }

    @Override
    public OptionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from ( context ).inflate ( R.layout.survey_options_recycler_item, null, true );
        return new OptionHolder ( view );
    }

    @Override
    public void onBindViewHolder(OptionHolder holder, int position) {
        holder.bindOption ( optionList.get ( position ) );
    }

    @Override
    public int getItemCount() {
        return optionList.size ();
    }

    public class OptionHolder extends RecyclerView.ViewHolder {

        private RadioButton optionRadioBtn;
        private Option option;

        public OptionHolder(View itemView) {
            super ( itemView );
            optionRadioBtn = (RadioButton) itemView.findViewById ( R.id.option_radioBtn );
        }

        public void bindOption(Option option) {
            this.option = option;
            optionRadioBtn.setText ( option.getText () );
        }
    }
}
