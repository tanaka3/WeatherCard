package net.masaya3.weather.fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import net.masaya3.weather.R;

/**
 * Created by masaya3 on 2017/08/05.
 */

public class CardSelectFragmentDialog extends DialogFragment implements View.OnClickListener {



    public interface OnCardEventListener{
        void onPrint(long id);
        void onTrade(long id);
        void onDelete(long id);
    }

    private OnCardEventListener onCardEventListener;

    public void setOnCardEventListener(OnCardEventListener onCardEventListener) {
        this.onCardEventListener = onCardEventListener;
    }

    private long id;

    /**
     *
     * @return
     */
    public static CardSelectFragmentDialog newInstance(long id){
        CardSelectFragmentDialog dialog = new CardSelectFragmentDialog();
        Bundle args = new Bundle();
        args.putLong("id", id);
        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Dialog dialog = super.onCreateDialog(savedInstanceState);
        //dialog.setCancelable(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.cardselect_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);

        return dialog;
    }

    /**
     *
     * @param i
     * @param c
     * @param b
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater i, ViewGroup c, Bundle b)
    {

        Bundle args = getArguments();

        id= args.getLong("id");

        View content = i.inflate(R.layout.cardselect_dialog, null);


        content.findViewById(R.id.card_trade).setOnClickListener(this);
        content.findViewById(R.id.card_print).setOnClickListener(this);
        content.findViewById(R.id.card_delete).setOnClickListener(this);

        return content;
    }

    @Override
    public void onClick(View view) {

        if(onCardEventListener != null){
            switch (view.getId()){
                case R.id.card_trade:
                    onCardEventListener.onTrade(id);
                    break;
                case R.id.card_print:
                    onCardEventListener.onPrint(id);
                    break;
                case R.id.card_delete:
                    onCardEventListener.onDelete(id);
                    break;

            }
        }



        this.dismiss();
    }
}
