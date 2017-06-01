package net.smartworks.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;


/* keyword 관련 작업  */
public class InsertKeyWordTask {
	
	/* 키워드 추출 */
	public static List<String> getKeyWord(Task task) {
		List<String> list = new ArrayList<String>();

		/* 키워드 추출 */ 
		String title = task.getTitle();
		String[] titles = StringUtils.tokenizeToStringArray(title, " ");				// 공백을 기준으로 split
		
		String content = task.getContents();											// 공백을 기준으로 split
		String[] contents = StringUtils.tokenizeToStringArray(content, " ");
		
		SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");		// 날짜를 String으로 변경 
		String date = transFormat.format(task.getCreatedDate());
		
		/* 추출한 키워드를 list 에 담아 놓는다. */ 
		for(int i=0; i<titles.length; i++) {
			list.add(titles[i]);			
		}

		for(int i=0; i<contents.length; i++) {
			list.add(contents[i]);
		}
		list.add(date);

		List<String> keyword = new ArrayList<String>(new HashSet<String>(list));		// list에 들어있는 중복값을 제거한다. (대신 순서가 사라짐)
		
		return keyword;
	}
	
	/* 자음 종류 구별 */
	public static char getChoSung(char firstChar) {
		
		char[] arrChoSung = { 0x3131, 0x3132, 0x3134, 0x3137, 0x3138,					// 한글 초성 (자음) 처리용 
				0x3139, 0x3141, 0x3142, 0x3143, 0x3145, 0x3146, 0x3147, 0x3148,
				0x3149, 0x314a, 0x314b, 0x314c, 0x314d, 0x314e };
		
		char chosung = (char) (firstChar - 0xAC00);										// 초성(자음) 추출 
		int chosungNum = chosung / (21 * 28);
		firstChar = arrChoSung[chosungNum];
		
		return firstChar;
	}
	
	
	/* 키워드가 한글일 경우, 경우에 따른 테이블이름 정하기 */
	public static String getHanguelTableName(char firstChar) {
		
		String tableName = "";
		int number = -1;
		
		char[] hanguel_Consonant = {'ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ', 'ㄹ', 'ㅁ', 'ㅂ', 'ㅃ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'};
		char[] hanguel_Vowel = {'ㅏ', 'ㅐ', 'ㅑ', 'ㅒ', 'ㅓ', 'ㅔ', 'ㅕ', 'ㅖ', 'ㅗ', 'ㅘ', 'ㅙ', 'ㅚ', 'ㅛ', 'ㅜ', 'ㅝ', 'ㅞ', 'ㅟ', 'ㅠ', 'ㅡ', 'ㅢ', 'ㅣ'};
		String[] hangeulToalphabet = {"ga", "gga", "na", "da", "dda", "ra", "ma", "ba", "bba", "sa", "ssa", "aa", "ja", "jja",
				"cha", "ka", "ta", "pa", "ha"
		};
		
		if( !(firstChar >= 'ㅏ' && firstChar <= 'ㅣ')) {									// 자음일 경우  
			
			for(int i=0; i<hanguel_Consonant.length; i++) {								// 자음에 해당하는 임의로 지정한 알파벳을 꺼낸다. (테이블이름에 사용하기 위함) 
				if(firstChar == hanguel_Consonant[i]) {
					number = i;
				}
			}
			
			try{
				tableName = hangeulToalphabet[number];
			} catch(Exception e) {
				System.out.println(e.toString());
			}
			
			
		} else {																		// 모음일 경우
			tableName = "vowel";														// 테이블 이름을 'vowel'로 지정. 
		}
		
		return tableName;
	}
	
	
	/* 키워드가 숫자일경우, 그에 해당하는 테이블이름 정하기 */
	public static String getNumberTableName(char firstChar) {

		String num = "" + firstChar;
		int index = Integer.parseInt(num);
		String tableName = "";
		
		String[] numberName = {"zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};
		
		tableName = numberName[index];
		return tableName;
	}
	
	
}
