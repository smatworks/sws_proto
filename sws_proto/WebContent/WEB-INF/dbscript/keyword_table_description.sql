- 키워드별 테이블이름 설명 -

  키워드의 맨 첫글자를 기준으로 테이블 이름을 정한다.

* 키워드 A-Z || a-z 까지는 = 'keyword + 첫글자' 이다.
  ex) - 'apple' 키워드는 keyworda 테이블에 Insert 된다.

* 키워드 한글 한글자or자음 까지는 -> 가-힣 || ㄱ-ㅎ 까지 = 자음의 첫 글자 발음 그대로 영어로 + keyword이다.  즉, 순서대로 
  'keyword' + {"ga", "gga", "na", "da", "dda", "ra", "ma", "ba", "bba", "sa", "ssa", "aa", "ja", "jja", "cha", "ka", "ta", "pa", "ha"}; 이다.
  ex) - '과자' 키워드는 keywordga 테이블에 Insert 된다.
  
  &참조 (한글 맞춤법 제4항)
  자음(19자) : ㄱ ㄲ ㄴ ㄷ ㄸ ㄹ ㅁ ㅂ ㅃ ㅅ ㅆ ㅇ ㅈ ㅉ ㅊ ㅋ ㅌ ㅍ ㅎ
  모음(21자) : ㅏ ㅐ ㅑ ㅒ ㅓ ㅔ ㅕ ㅖ ㅗ ㅘ ㅙ ㅚ ㅛ ㅜ ㅝ ㅞ ㅟ ㅠ ㅡ ㅢ ㅣ
  받침(27자) : ㄱ ㄲ ㄳ ㄴ ㄵ ㄶ ㄷ ㄹ ㄺ ㄻ ㄼ ㄽ ㄾ ㄿ ㅀ ㅁ ㅂ ㅄ ㅅ ㅆ ㅇ ㅈ ㅊ ㅋ ㅌ ㅍ ㅎ
  
* 키워드 한글 모음 ㅏ-ㅣ 까지 'keyword + vowel' 이다. 
  ex) - 'ㅑ' 키워드는 keywordvowel 테이블에 Insert 된다.

* 키워드 숫자는 0-9까지 = 'keyword' + 숫자를 영어로바꾼것 이다.
  ex) - '3' 키워드는 keywordthree 테이블에 Insert 된다. 
  
* 키워드 특수문자 = 'keyword + speicalletter' 이다.
  ex) - '#' 키워드는 keywordspeicalletter 테이블에 Insert 된다.
  