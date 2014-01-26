package br.com.layout;

import java.util.List;

import br.com.achei.R;
import br.com.achei.R.id;
import br.com.achei.R.layout;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AdapterListView extends BaseAdapter {

	private LayoutInflater mInflater;

	private List<ItemListView> itens;

	public AdapterListView(Context context, List<ItemListView> itens) {
		// Itens do listview
		this.itens = itens; // Objeto responsável por pegar o Layout do item.
		mInflater = LayoutInflater.from(context);
	}

	public int getCount() {
		return itens.size();
	}

	public ItemListView getItem(int position) {
		return itens.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		ItemSuporte itemHolder;

		if (view == null) {
			view = mInflater.inflate(R.layout.item_list, null);

			itemHolder = new ItemSuporte();
			itemHolder.txtTitle = ((TextView) view.findViewById(R.id.text));
			itemHolder.imgIcon = ((ImageView) view
					.findViewById(R.id.imagemview));

			view.setTag(itemHolder);
		} else {
			itemHolder = (ItemSuporte) view.getTag();
		}

		ItemListView item = itens.get(position);
		itemHolder.txtTitle.setText(item.getTexto());
		itemHolder.imgIcon.setImageResource(item.getIconeRid());

		return view;
	}

	private class ItemSuporte {
		ImageView imgIcon;
		TextView txtTitle;
	}

}