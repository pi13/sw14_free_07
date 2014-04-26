package at.lvmaster3000.gui;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import at.lvmaster3000.R;

public class LecturesFragment extends Fragment{
	
	public LecturesFragment() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_lectures, container, false);
         
        return rootView;
    }
}
