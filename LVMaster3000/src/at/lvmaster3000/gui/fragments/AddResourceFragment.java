package at.lvmaster3000.gui.fragments;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import at.lvmaster3000.R;
import at.lvmaster3000.database.IDBlogic;
import at.lvmaster3000.database.objects.Lecture;
import at.lvmaster3000.database.objects.Resource;
import at.lvmaster3000.gui.interfaces.IDialogListener;

public class AddResourceFragment extends DialogFragment implements	OnClickListener {

	private IDialogListener dialogListener;

	private Context context;
	private Resource resource;
	private EditText resTitle;
	
	private long lectureId;	
	private IDBlogic dbLogic;

	public static AddResourceFragment newInstance(Context context) {
		AddResourceFragment dialog = new AddResourceFragment();
		dialog.context = context;
		return dialog;
	}

	public Resource getResource() {
		return resource;
	}

	// Override the Fragment.onAttach() method to instantiate the
	// NoticeDialogListener
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		// Verify that the host activity implements the callback interface
		try {
			// Instantiate the NoticeDialogListener so we can send events to the
			// host
			dialogListener = (IDialogListener) activity;
		} catch (ClassCastException e) {
			// The activity doesn't implement the interface, throw exception
			throw new ClassCastException(activity.toString()
					+ " must implement NoticeDialogListener");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.add_resource_fragment, container);

		Button ok = (Button) view.findViewById(R.id.ok_btn);
		Button cancel = (Button) view.findViewById(R.id.cancel_btn);
		ok.setOnClickListener(this);
		cancel.setOnClickListener(this);

		resTitle = (EditText) view.findViewById(R.id.add_res_title);
		
		Bundle bundle = getArguments();
		if(bundle != null) {
			this.lectureId = bundle.getLong("lectureId");		
			this.dbLogic = new IDBlogic(context);
		}

		return view;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ok_btn:
			String value = resTitle.getText().toString();
			if (value != null) {
				resource = new Resource(value);
				
				if(this.lectureId > 0) {
					Lecture lecture = new Lecture();
					lecture.setID(this.lectureId);
					this.dbLogic.addResourceToLecture(resource, lecture);
					dialogListener.onResourceAddToLecture(this);
					
				} else {
					dialogListener.onResourceAdd(this);
				}
				
			}else
			{
				dialogListener.onDialogNegativeClick(this);
			}
			
			break;

		case R.id.cancel_btn:
			dialogListener.onDialogNegativeClick(this);
			break;
		}
	}
}
