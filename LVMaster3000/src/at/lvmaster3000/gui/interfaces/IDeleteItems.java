package at.lvmaster3000.gui.interfaces;

import at.lvmaster3000.database.objects.Date;
import at.lvmaster3000.database.objects.Exam;
import at.lvmaster3000.database.objects.Lecture;
import at.lvmaster3000.database.objects.Resource;
import at.lvmaster3000.database.objects.Task;

public interface IDeleteItems {

	void DeleteLectureItem(Lecture lecture);
	void DeleteExamItem(Exam exam);
	void DeleteTaskItem(Task task);
	void DeleteResourceItem(Resource resource);
	void DeleteDateItem(Date date);
	void DeleteItem(Object item);
}
