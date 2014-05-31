package at.lvmaster3000.database;

import android.content.Context;
import at.lvmaster3000.database.demodata.DDTestsetA;
import at.lvmaster3000.database.lists.Lectures;
import at.lvmaster3000.database.logic.DBLLectures;
import at.lvmaster3000.database.objects.Lecture;

public class IDBlogic {

	/**
	 * 
	 */
	private DBLLectures lectures = null;
	
	/**
	 * 
	 * @param context
	 */
	public IDBlogic(Context context) {
		DDTestsetA TestA = new DDTestsetA(context);
        TestA.FillDb();
		
		this.lectures = new DBLLectures(context);
	}
	
	/**
	 * 
	 * @param lecture
	 * @return	long
	 */
	public long addLecture(Lecture lecture) {
		return this.lectures.addLecture(lecture);
	}
	
	/**
	 * 
	 * @param lectureId
	 * @return int
	 */
	public int deleteLecture(long lectureId) {
		return this.lectures.deleteLecture(lectureId);
	}
	
	/**
	 * 
	 * @param limit	If 0, all lectures are returned
	 * @return Lectures (Object)
	 */
	public Lectures getLectures(int limit) {
		return this.lectures.getLectures(limit);
	}
	
	/**
	 * 
	 * @param lectureID
	 * @return
	 */
	public Lecture getLectureByID(long lectureID) {
		return this.lectures.getLectureByID(lectureID);
	}
	
}
