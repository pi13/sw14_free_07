package at.lvmaster3000.database.lists;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import at.lvmaster3000.database.objects.Exam;

public class Exams {
	private List<Exam> exams;
	
	public Exams() {
		this.exams = new ArrayList<Exam>();
	}
	
	public void addExam(Exam exam) {
		this.exams.add(exam);		
	}
	
	public List<Exam> getExam() {
		return this.exams;
	}
	
	public void cursorToExamList(Cursor cursor) {
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			this.exams.add(new Exam().cursorToExam(cursor));
			cursor.moveToNext();
		}
	}
	
	public void printExamList() {
		for(int i = 0; i < this.exams.size(); i++) {
			this.exams.get(i).printExam();
		}
	}

	public int nrOfExams() {
		return this.exams.size();
	}
}
