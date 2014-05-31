package at.lvmaster3000.gui.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import at.lvmaster3000.R;
import at.lvmaster3000.database.IDBlogic;
import at.lvmaster3000.gui.adapters.ExamListAdapter;

public class ExamsFragment extends UIFragmentBase {
	private IDBlogic idLogic;
	
	public ExamsFragment() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        idLogic = new IDBlogic(this.getActivity().getApplicationContext());
		final ListView list = (ListView) inflater.inflate(R.layout.fragment_list_layout, container, false);
        attachAdapter(list);
        //list.setOnItemClickListener(this);
        return list;
    }
	
	private void attachAdapter(ListView list)
	{
		ExamListAdapter examLA = new ExamListAdapter(list.getContext().getApplicationContext(), idLogic.getExams(0).getExam());
		list.setAdapter(examLA);
	}
}
