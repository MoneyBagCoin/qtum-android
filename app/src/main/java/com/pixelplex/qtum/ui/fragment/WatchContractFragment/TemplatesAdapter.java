package com.pixelplex.qtum.ui.fragment.WatchContractFragment;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pixelplex.qtum.R;
import com.pixelplex.qtum.model.ContractTemplate;

import java.util.List;

public class TemplatesAdapter extends RecyclerView.Adapter<TemplateHolder> {

    List<ContractTemplate> mContractTemplates;
    OnTemplateClickListener mListener;
    int resId;
    int selectionColor;

    public TemplatesAdapter(List<ContractTemplate> contractTemplates, OnTemplateClickListener listener, int resId, int selectionColor) {
        mContractTemplates = contractTemplates;
        mListener = listener;
        this.resId = resId;
        this.selectionColor = selectionColor;
    }

    @Override
    public TemplateHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(resId, parent, false);
        return new TemplateHolder(view, mListener, selectionColor);
    }

    @Override
    public void onBindViewHolder(TemplateHolder holder, int position) {
        holder.onBind(mContractTemplates.get(position));
    }

    @Override
    public int getItemCount() {
        return mContractTemplates.size();
    }
}
