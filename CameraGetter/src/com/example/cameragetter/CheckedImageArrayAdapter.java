package com.example.cameragetter;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;  
import android.graphics.Color;
import android.view.LayoutInflater;  
import android.view.View;  
import android.view.ViewGroup;  
import android.widget.ArrayAdapter;  
import android.widget.CheckBox;  
import android.widget.CompoundButton;  
import android.widget.ImageView;  
import android.widget.CompoundButton.OnCheckedChangeListener;  
  
  
public class CheckedImageArrayAdapter extends ArrayAdapter<CheckedImage> {  
    private static class ViewHolder {  
        int position;  
        ImageView imageview = null;  
        CheckBox checkbox = null;  
    }  
  
    private final static int LAYOUT_ID = R.layout.list_item;
  
  
    private Context mContext;  
    private LayoutInflater mInflater;     
  
  
    public CheckedImageArrayAdapter(Context context, List<CheckedImage> objects) {  
        super(context, LAYOUT_ID, objects);  
        mContext = context;  
        mInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);   
    }  
  
    @Override  
    public View getView(int position, View convertView, ViewGroup parent) {  
        final ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(LAYOUT_ID, null);  
            holder = new ViewHolder();  
            holder.imageview = (ImageView) convertView.findViewById(R.id.image);  
            holder.checkbox = (CheckBox) convertView.findViewById(R.id.check);  
            holder.checkbox.setOnCheckedChangeListener(CheckBox1_OnCheckedChangeListener);  
            convertView.setTag(holder);  
        } else {  
            holder = (ViewHolder)convertView.getTag();  
        }  
  
        convertView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO 自動生成されたメソッド・スタブ
				if(holder.checkbox.isChecked()){
					holder.checkbox.setChecked(false);
				} else {
					holder.checkbox.setChecked(true);
				}
			}
		});
        CheckedImage item = getItem(position);  
        holder.position = position;  
        holder.imageview.setImageBitmap(item.getBitmap(mContext));  
        holder.checkbox.setChecked(item.getChecked());  
  
        return convertView;  
    }  
  
    public List<CheckedImage> getCheckedItem(){  
        List<CheckedImage> lstItem = new ArrayList<CheckedImage>();  
        for ( int i = 0; i < getCount(); i++) {  
            if (getItem(i).getChecked()){  
                lstItem.add(getItem(i));  
            }  
        }  
        return lstItem;  
    }  
    
    public List<Long> getCheckedItemId(){
        List<Long> lstItemId = new ArrayList<Long>();  
        for ( int i = 0; i < getCount(); i++) {  
            if (getItem(i).getChecked()){  
                lstItemId.add(getItem(i).getBitmapId());  
            }  
        }                 	
    	return lstItemId;
    }
    
  
    private OnCheckedChangeListener CheckBox1_OnCheckedChangeListener = new OnCheckedChangeListener(){  
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {  
            View view = (View)buttonView.getParent();  
            ViewHolder holder = (ViewHolder)view.getTag();  
            CheckedImage item = CheckedImageArrayAdapter.this.getItem(holder.position);  
            item.setChecked(isChecked);          
        }};  
}  


/*import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.view.View.OnTouchListener;
 
public class BitmapAdapter extends ArrayAdapter<Bitmap> {
 
    private int resourceId;
 
    public BitmapAdapter(Context context, int resource, List<Bitmap> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }
    
    private static class ViewHolder {
        public ImageView imageView;
        public CheckBox checkBox;
     }
    
 
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(resourceId, null);
            holder = new ViewHolder();
            holder.imageView = (ImageView)convertView.findViewById(R.id.image);
            holder.checkBox = (CheckBox)convertView.findViewById(R.id.check);
            convertView.setTag(holder);
        } else {
        	holder = (ViewHolder)convertView.getTag();
        }
 
        
        holder.imageView.setImageBitmap(getItem(position));
        convertView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO 自動生成されたメソッド・スタブ
				if(holder.checkBox.isChecked()){
					holder.checkBox.setChecked(false);
				} else {
					holder.checkBox.setChecked(true);
				}
			}
		});
         
        return convertView;
    }
 
}*/
