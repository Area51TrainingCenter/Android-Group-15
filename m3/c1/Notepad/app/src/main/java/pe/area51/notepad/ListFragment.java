package pe.area51.notepad;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListFragment extends Fragment {

    private ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_list, container, false);
        listView = (ListView) view.findViewById(R.id.fragment_list_listview_list);
        listView.setAdapter(new NotesListViewAdapter(this));
        return view;
    }

    public ListView getListView() {
        return listView;
    }

    public static class NotesListViewAdapter extends ArrayAdapter<Note> {

        private final Fragment fragment;

        public NotesListViewAdapter(final Fragment fragment) {
            super(fragment.getActivity(), 0, new ArrayList<Note>());
            this.fragment = fragment;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final View listElement = fragment.getActivity().getLayoutInflater().inflate(R.layout.fragment_list_listview_element, null);
            final TextView titleTextView = (TextView) listElement.findViewById(R.id.fragment_list_listview_element_title);
            titleTextView.setText(getItem(position).getTitle());
            return listElement;
        }
    }
}
