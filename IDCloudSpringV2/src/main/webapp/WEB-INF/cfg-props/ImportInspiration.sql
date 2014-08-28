insert into IDCLOUD.INSPIRATION (INSPIRATION_TITLE, INSPIRATION_LOCATION, INSPIRATION_POSTTIME) 
values ('John''s First Web Page', '/My Weblog_files/firstweb.html', '2014-08-07 17:45:00');

insert into IDCLOUD.INSPIRATION (INSPIRATION_TITLE, INSPIRATION_LOCATION, INSPIRATION_POSTTIME) 
values ('Follow The Movie English Learning', '/followthemovie/FollowTheMovieCloud.html', '2014-08-07 17:45:00');

insert into IDCLOUD.TAG (TAG_NAME, TAG_COLOR)
values ('English', 0x113e78);

insert into IDCLOUD.TAG (TAG_NAME, TAG_COLOR)
values ('Cloud', 0xEC115A);

insert into IDCLOUD.TAG (TAG_NAME, TAG_COLOR)
values ('Movie', 0xF5FF00);

insert into IDCLOUD.TAG (TAG_NAME, TAG_COLOR)
values ('HTML5', 0x14295F);

insert into IDCLOUD.INSPIRATION_M2M_TAG (INSPIRATION_ID, TAG_ID)
values (1,4);

insert into IDCLOUD.INSPIRATION_M2M_TAG (INSPIRATION_ID, TAG_ID)
values (2,1);

insert into IDCLOUD.INSPIRATION_M2M_TAG (INSPIRATION_ID, TAG_ID)
values (2,2);

insert into IDCLOUD.INSPIRATION_M2M_TAG (INSPIRATION_ID, TAG_ID)
values (2,3);