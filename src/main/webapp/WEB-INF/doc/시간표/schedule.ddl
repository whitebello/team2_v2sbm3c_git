/**********************************/
/* Table Name: 시간표 */
/**********************************/
DROP TABLE schedule;
CREATE TABLE schedule(
		classno                     		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		classname                         		VARCHAR2(100)		 NOT NULL,
        memberno                              NUMBER(10)      NOT NULL,
        cday                                      VARCHAR2(100)   NOT NULL,
        professor                               VARCHAR2(100)   NOT NULL,
		starttime                           		NUMBER(7)		 NOT NULL,
        endtime                           		NUMBER(7)		 NOT NULL,
        textbook                                VARCHAR2(100)  NULL,
        FOREIGN KEY (memberno) REFERENCES member (memberno)
);

-- pstatus : 0: 미구매 1:구매
COMMENT ON TABLE schedule is '시간표';
COMMENT ON COLUMN schedule.classno is '강의 번호';
COMMENT ON COLUMN schedule.classname is '강의명';
COMMENT ON COLUMN schedule.starttime is '시작시간';
COMMENT ON COLUMN schedule.cday is '강의일';
COMMENT ON COLUMN schedule.endtime is '종료시간';
COMMENT ON COLUMN schedule.memberno is '회원번호';
COMMENT ON COLUMN schedule.textbook is '교제';
COMMENT ON COLUMN schedule.professor is '교수';

DROP SEQUENCE schedule_seq;
CREATE SEQUENCE schedule_seq
  START WITH 1              -- 시작 번호
  INCREMENT BY 1          -- 증가값
  MAXVALUE 9999999999 -- 최대값: 9999999999
  CACHE 2                     -- 2번은 메모리에서만 계산
  NOCYCLE;                   -- 다시 1부터 생성되는 것을 방지

-- 문자열 테스트  
INSERT INTO schedule(classno, memberno, cday, classname, professor, starttime, endtime, textbook)
VALUES(schedule_seq.nextval, 1, 0, '어둠의마법학', '스네이프', 9, 11, '약초학 개정3판');

INSERT INTO schedule(classno, memberno, cday, classname, professor, starttime, endtime, textbook)
VALUES(schedule_seq.nextval, 1, 0, '해리포터학개론', '해리포터', 14, 16, '볼드모트잡는법 개정2판');

INSERT INTO schedule(classno, memberno, cday, classname, professor, starttime, endtime, textbook)
VALUES(schedule_seq.nextval, 1, 1, '사랑학개론', '백종환', 9, 11, '사랑이제일쉬웠어요');

INSERT INTO schedule(classno, memberno, cday, classname, starttime, endtime)
VALUES(schedule_seq.nextval, 2, 'tue', 'SPRING', 15, 16);

-- 정수형 테스트
INSERT INTO schedule(classno, memberno, cday, classname, starttime, endtime)
VALUES(schedule_seq.nextval, 2, 0, 'DB', 9, 11);

INSERT INTO schedule(classno, memberno, cday, classname, starttime, endtime)
VALUES(schedule_seq.nextval, 2, 1, 'DBA', 12, 16);

INSERT INTO schedule(classno, memberno, cday, classname, starttime, endtime)
VALUES(schedule_seq.nextval, 2, 2, 'SPRING', 15, 16);

INSERT INTO schedule(classno, memberno, cday, classname, starttime, endtime)
VALUES(schedule_seq.nextval, 1, 'tue', 'SPRING', 15, 16);

commit;
    SELECT classno, memberno, cday, classname, professor, starttime, endtime, textbook
    FROM schedule
    WHERE memberno = 2;
    
        SELECT classno, memberno, cday, classname, starttime, endtime, professor, textbook
    FROM schedule
    WHERE  classno = 1;
    
