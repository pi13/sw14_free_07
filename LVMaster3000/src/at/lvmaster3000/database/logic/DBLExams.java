package at.lvmaster3000.database.logic;

import android.content.Context;
import at.lvmaster3000.database.lists.Coworkers;
import at.lvmaster3000.database.lists.Exams;
import at.lvmaster3000.database.lists.Resources;
import at.lvmaster3000.database.objects.Date;
import at.lvmaster3000.database.objects.Exam;

public class DBLExams {

	public DBLExams(Context context) {
		// TODO Auto-generated constructor stub
	}

	public long addExam(Exam exam) {
		// TODO Auto-generated method stub
		return -1;
	}

	public long addExam(String title, String comment, long lecture_id){
		// TODO method stub
		return -1;
	}
	
	public Exams getAllExams() {
		// TODO Auto-generated method stub
		return null;
	}

	public void deleteExam(long id) {
		// TODO Auto-generated method stub
		
	}

	public void editExam(Exam editedExam) {
		// TODO Auto-generated method stub
		
	}

	public Exam getExamById(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean setDateToExam(long examId, Date date) {
		// TODO Auto-generated method stub
		return false;
	}

	public Resources getAllResourcesOfExam(long examId) {
		// TODO Auto-generated method stub
		return null;
	}

	public Coworkers getAllCoworkersOfExam(long examId){
		// TODO Auto-generated method stub
		return null;
	}


}
