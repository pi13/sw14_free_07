package at.lvmaster3000.gui.interfaces;

import at.lvmaster3000.database.objects.Date;
import at.lvmaster3000.database.objects.Exam;
import at.lvmaster3000.database.objects.Resource;
import at.lvmaster3000.database.objects.Task;

public interface IExpandableListItemSelected {

	void onExpandableDateSelected(Date date);
	void onExpandableExamSelected(Exam exam);
	void onExpandableTaskSelected(Task task);
	void onExpandableResourceSelected(Resource resource);
	
	void onExpandableDateDeleted(Date date);
	void onExpandableExamDeleted(Exam exam);
	void onExpandableTaskDeleted(Task task);
	void onExpandableResourceDeleted(Resource resource);
}
