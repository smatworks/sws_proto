
CREATE TABLE TASK (
	objId character varying(100) NOT NULL,
	title character varying(500),
	contents character varying(4000),
	taskType character varying(50),
	tags character varying(4000),
	completeYn boolean,
	createdUser character varying(50),
	createdDate timestamp,
	startDate timestamp,
	endDate timestamp,
	primary key (objId)	
);

CREATE TABLE TASK_HISTORY (
	objId character varying(100) NOT NULL,
	tskObjId character varying(100),
	historyjson text,
	primary key (objId)	
);
