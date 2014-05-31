package at.lvmaster3000.gui.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import at.lvmaster3000.R;
import at.lvmaster3000.database.IDBlogic;
import at.lvmaster3000.gui.adapters.ExamListAdapter;

public class ExamsFragment extends UIFragmentBase {
	private IDBlogic dbLogic;
	private Context context;
	
	public ExamsFragment() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		
		this.context = this.getActivity().getApplicationContext();
		dbLogic = new IDBlogic(this.context);
        
        ListView list = (ListView) inflater.inflate(R.layout.fragment_list_layout, container, false);
		ExamListAdapter adapter = new ExamListAdapter(this.context, dbLogic.getExams(0).getExam());
        attachAdapter(list, adapter);

        return list;
    }

}
