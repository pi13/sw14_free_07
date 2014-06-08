package at.lvmaster3000.gui.interfaces;

import android.app.DialogFragment;

public interface IDialogListener {
	/*
	 * The activity that creates an instance of this dialog fragment must
	 * implement this interface in order to receive event callbacks. Each method
	 * passes the DialogFragment in case the host needs to query it.
	 */
	public void onLectureAdd(DialogFragment dialog);
	
	public void onResourceAdd(DialogFragment dialog);
	
	public void onExamAdd(DialogFragment dialog);
	
	public void onTaskAdd(DialogFragment dialog);

	public void onDialogNegativeClick(DialogFragment dialog);
}
