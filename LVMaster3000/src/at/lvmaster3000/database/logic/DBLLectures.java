package at.lvmaster3000.database.logic;

import android.content.Context;
import at.lvmaster3000.database.lists.Exams;
import at.lvmaster3000.database.lists.Lectures;
import at.lvmaster3000.database.lists.Tasks;
import at.lvmaster3000.database.objects.Lecture;

public class DBLLectures {

	public DBLLectures(Context context) {
		// TODO Auto-generated constructor stub
	}

	public Lectures getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public long addLecture(Lecture lecture) {
		// TODO Auto-generated method stub
		return -1l;
	}

	public long addLecture(String number, String name,
			String scomment, String type, int required, int compulsory) {
		// TODO Auto-generated method stub
		return -1l;
	}

	public void deleteLecture(Lecture lecture) {
		// TODO Auto-generated method stub
		
	}

	public Lecture getLectureByNumber(String string) {
		// TODO Auto-generated method stub
		return null;
	}

	public Tasks getTasksForLecture(Lecture lecture) {
		// TODO Auto-generated method stub
		return null;
	}

	public Lectures getLectures() {
		// TODO Auto-generated method stub
		return null;
	}

	public Exams getExamsForLecture(Lecture lecture) {
		// TODO Auto-generated method stub
		return null;
	}

}
