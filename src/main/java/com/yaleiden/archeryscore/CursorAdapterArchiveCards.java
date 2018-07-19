package com.yaleiden.archeryscore;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;

public class CursorAdapterArchiveCards extends SimpleCursorAdapter {

    private String TAG = "EntryListCursorAdapter";

    public CursorAdapterArchiveCards(Context context, int layout, Cursor c,
                                     String[] from, int[] to) {
        super(context, layout, c, from, to, 0);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView textViewAname = (TextView) view
                .findViewById(R.id.textViewAname);
        TextView textViewDate = (TextView)
                view.findViewById(R.id.textViewDate);
        TextView textViewtotal = (TextView) view
                .findViewById(R.id.textViewtotal);
        TextView textViewCourse = (TextView) view
                .findViewById(R.id.textViewCourse);

        TextView textViewT1 = (TextView) view.findViewById(R.id.textViewT1);
        TextView textViewT2 = (TextView) view.findViewById(R.id.textViewT2);
        TextView textViewT3 = (TextView) view.findViewById(R.id.textViewT3);
        TextView textViewT4 = (TextView) view.findViewById(R.id.textViewT4);
        TextView textViewT5 = (TextView) view.findViewById(R.id.textViewT5);
        TextView textViewT6 = (TextView) view.findViewById(R.id.textViewT6);
        TextView textViewT7 = (TextView) view.findViewById(R.id.textViewT7);
        TextView textViewT8 = (TextView) view.findViewById(R.id.textViewT8);
        TextView textViewT9 = (TextView) view.findViewById(R.id.textViewT9);
        TextView textViewT10 = (TextView) view.findViewById(R.id.textViewT10);
        TextView textViewT11 = (TextView) view.findViewById(R.id.textViewT11);
        TextView textViewT12 = (TextView) view.findViewById(R.id.textViewT12);
        TextView textViewT13 = (TextView) view.findViewById(R.id.textViewT13);
        TextView textViewT14 = (TextView) view.findViewById(R.id.textViewT14);
        TextView textViewT15 = (TextView) view.findViewById(R.id.textViewT15);
        TextView textViewT16 = (TextView) view.findViewById(R.id.textViewT16);
        TextView textViewT17 = (TextView) view.findViewById(R.id.textViewT17);
        TextView textViewT18 = (TextView) view.findViewById(R.id.textViewT18);
        TextView textViewT19 = (TextView) view.findViewById(R.id.textViewT19);
        TextView textViewT20 = (TextView) view.findViewById(R.id.textViewT20);


        TextView textViewB1 = (TextView) view.findViewById(R.id.textViewB1);
        TextView textViewB2 = (TextView) view.findViewById(R.id.textViewB2);
        TextView textViewB3 = (TextView) view.findViewById(R.id.textViewB3);
        TextView textViewC1 = (TextView) view.findViewById(R.id.textViewC1);
        //TextView textViewC2 = (TextView) view.findViewById(R.id.textViewC2);

        String Targets = cursor.getString(55);
        int numTargets = Integer.valueOf(Targets);

        textViewAname.setText(cursor.getString(0));
        textViewDate.setText(cursor.getString(1));
        textViewtotal.setText(cursor.getString(2) + "/" + cursor.getString(3));
        textViewCourse.setText(cursor.getString(4));


        textViewT1.setText(cursor.getString(5));
        textViewT2.setText(cursor.getString(6));
        textViewT3.setText(cursor.getString(7));
        textViewT4.setText(cursor.getString(8));
        textViewT5.setText(cursor.getString(9));

        textViewT6.setText(cursor.getString(10));
        textViewT7.setText(cursor.getString(11));
        textViewT8.setText(cursor.getString(12));
        textViewT9.setText(cursor.getString(13));
        textViewT10.setText(cursor.getString(14));

        textViewT11.setText(cursor.getString(15));
        textViewT12.setText(cursor.getString(16));
        textViewT13.setText(cursor.getString(17));
        textViewT14.setText(cursor.getString(18));
        textViewT15.setText(cursor.getString(19));

        textViewT16.setText(cursor.getString(20));
        textViewT17.setText(cursor.getString(21));
        textViewT18.setText(cursor.getString(22));
        textViewT19.setText(cursor.getString(23));
        textViewT20.setText(cursor.getString(24));


        if (numTargets > 20) {
            TableRow tableRow5 = (TableRow) view.findViewById(R.id.tableRow5);
            tableRow5.setVisibility(View.VISIBLE);

            TextView textViewT21 = (TextView) view.findViewById(R.id.textViewT21);
            TextView textViewT22 = (TextView) view.findViewById(R.id.textViewT22);
            TextView textViewT23 = (TextView) view.findViewById(R.id.textViewT23);
            TextView textViewT24 = (TextView) view.findViewById(R.id.textViewT24);
            TextView textViewT25 = (TextView) view.findViewById(R.id.textViewT25);

            textViewT21.setText(cursor.getString(25));
            textViewT22.setText(cursor.getString(26));
            textViewT23.setText(cursor.getString(27));
            textViewT24.setText(cursor.getString(28));
            textViewT25.setText(cursor.getString(29));
        } else {
            TableRow tableRow5 = (TableRow) view.findViewById(R.id.tableRow5);
            tableRow5.setVisibility(View.GONE);
        }

        if (numTargets > 25) {
            TableRow tableRow6 = (TableRow) view.findViewById(R.id.tableRow6);
            tableRow6.setVisibility(View.VISIBLE);

            TextView textViewT26 = (TextView) view.findViewById(R.id.textViewT26);
            TextView textViewT27 = (TextView) view.findViewById(R.id.textViewT27);
            TextView textViewT28 = (TextView) view.findViewById(R.id.textViewT28);
            TextView textViewT29 = (TextView) view.findViewById(R.id.textViewT29);
            TextView textViewT30 = (TextView) view.findViewById(R.id.textViewT30);

            textViewT26.setText(cursor.getString(30));
            textViewT27.setText(cursor.getString(31));
            textViewT28.setText(cursor.getString(32));
            textViewT29.setText(cursor.getString(33));
            textViewT30.setText(cursor.getString(34));

        } else {
            TableRow tableRow6 = (TableRow) view.findViewById(R.id.tableRow6);
            tableRow6.setVisibility(View.GONE);
        }

        if (numTargets > 30) {
            TableRow tableRow7 = (TableRow) view.findViewById(R.id.tableRow7);
            tableRow7.setVisibility(View.VISIBLE);

            TextView textViewT31 = (TextView) view.findViewById(R.id.textViewT31);
            TextView textViewT32 = (TextView) view.findViewById(R.id.textViewT32);
            TextView textViewT33 = (TextView) view.findViewById(R.id.textViewT33);
            TextView textViewT34 = (TextView) view.findViewById(R.id.textViewT34);
            TextView textViewT35 = (TextView) view.findViewById(R.id.textViewT35);

            textViewT31.setText(cursor.getString(35));
            textViewT32.setText(cursor.getString(36));
            textViewT33.setText(cursor.getString(37));
            textViewT34.setText(cursor.getString(38));
            textViewT35.setText(cursor.getString(39));

        } else {
            TableRow tableRow7 = (TableRow) view.findViewById(R.id.tableRow7);
            tableRow7.setVisibility(View.GONE);
        }

        if (numTargets > 35) {
            TableRow tableRow8 = (TableRow) view.findViewById(R.id.tableRow8);
            tableRow8.setVisibility(View.VISIBLE);

            TextView textViewT36 = (TextView) view.findViewById(R.id.textViewT36);
            TextView textViewT37 = (TextView) view.findViewById(R.id.textViewT37);
            TextView textViewT38 = (TextView) view.findViewById(R.id.textViewT38);
            TextView textViewT39 = (TextView) view.findViewById(R.id.textViewT39);
            TextView textViewT40 = (TextView) view.findViewById(R.id.textViewT40);

            textViewT36.setText(cursor.getString(40));
            textViewT37.setText(cursor.getString(41));
            textViewT38.setText(cursor.getString(42));
            textViewT39.setText(cursor.getString(43));
            textViewT40.setText(cursor.getString(44));

        } else {
            TableRow tableRow8 = (TableRow) view.findViewById(R.id.tableRow8);
            tableRow8.setVisibility(View.GONE);
        }


        if (numTargets > 40) {
            TableRow tableRow9 = (TableRow) view.findViewById(R.id.tableRow9);
            tableRow9.setVisibility(View.VISIBLE);

            TextView textViewT41 = (TextView) view
                    .findViewById(R.id.textViewT41);
            TextView textViewT42 = (TextView) view
                    .findViewById(R.id.textViewT42);
            TextView textViewT43 = (TextView) view
                    .findViewById(R.id.textViewT43);
            TextView textViewT44 = (TextView) view
                    .findViewById(R.id.textViewT44);
            TextView textViewT45 = (TextView) view
                    .findViewById(R.id.textViewT45);

            textViewT41.setText(cursor.getString(45));
            textViewT42.setText(cursor.getString(46));
            textViewT43.setText(cursor.getString(47));
            textViewT44.setText(cursor.getString(48));
            textViewT45.setText(cursor.getString(49));
        } else {
            TableRow tableRow9 = (TableRow) view.findViewById(R.id.tableRow9);
            tableRow9.setVisibility(View.GONE);
        }

        if (numTargets > 45) {
            TableRow tableRow10 = (TableRow) view.findViewById(R.id.tableRow10);
            tableRow10.setVisibility(View.VISIBLE);
            TextView textViewT46 = (TextView) view
                    .findViewById(R.id.textViewT46);
            TextView textViewT47 = (TextView) view
                    .findViewById(R.id.textViewT47);
            TextView textViewT48 = (TextView) view
                    .findViewById(R.id.textViewT48);
            TextView textViewT49 = (TextView) view
                    .findViewById(R.id.textViewT49);
            TextView textViewT50 = (TextView) view
                    .findViewById(R.id.textViewT50);
            textViewT46.setText(cursor.getString(50));
            textViewT47.setText(cursor.getString(51));
            textViewT48.setText(cursor.getString(52));
            textViewT49.setText(cursor.getString(53));
            textViewT50.setText(cursor.getString(54));

        } else {
            TableRow tableRow10 = (TableRow) view.findViewById(R.id.tableRow10);
            tableRow10.setVisibility(View.GONE);
        }

        textViewB1.setText(cursor.getString(55));
        textViewB2.setText(cursor.getString(56) + "%");
        textViewB3.setText(cursor.getString(57));
        textViewC1.setText(cursor.getString(58));
        //textViewC2.setText(cursor.getString(58));

    }
}