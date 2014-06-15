package at.lvmaster3000.gui.interfaces;

import at.lvmaster3000.database.objects.Date;
import at.lvmaster3000.database.objects.Exam;
import at.lvmaster3000.database.objects.Lecture;
import at.lvmaster3000.database.objects.Resource;
import at.lvmaster3000.database.objects.Task;

public interface IUpdateDBObject {
	
	void updateLecture(Lecture lecture);
	void updateLectureExam(Exam exam);
	void updateTask(Task task);
	void updateResource(Resource resource);
	void updateDate(Date date);
}
