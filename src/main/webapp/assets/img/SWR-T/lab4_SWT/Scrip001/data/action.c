Action()
{

	web_url("css", 
		"URL=http://{host_fonts_googleapis_com}/css?family=Open+Sans:400,300,700,800", 
		"Resource=1", 
		"RecContentType=text/css", 
		"Referer=http://localhost:9999/", 
		"Snapshot=t1.inf", 
		LAST);

	web_set_sockets_option("SSL_VERSION", "AUTO");

	web_url("v3", 
		"URL=https://{host_edge_microsoft_com}/serviceexperimentation/v3/?osname=win&channel=stable&osver=10.0.22631&devicefamily=desktop&installdate=1655422490&clientversion=130.0.2849.56&experimentationmode=2&scpguard=1&scpfull=0&scpver=6", 
		"Resource=0", 
		"RecContentType=application/json", 
		"Referer=", 
		"Snapshot=t2.inf", 
		"Mode=HTML", 
		LAST);

	web_url("ExpandedDomainsFilterGlobal.json", 
		"URL=https://{host_www_bing_com}/bloomfilterfiles/ExpandedDomainsFilterGlobal.json", 
		"Resource=0", 
		"RecContentType=application/json", 
		"Referer=", 
		"Snapshot=t3.inf", 
		"Mode=HTML", 
		LAST);

	web_add_cookie("ANON=; DOMAIN=www.bing.com");

	web_add_cookie("MUID=0FCCB47D65F0660E1085A5B8643067DB; DOMAIN=www.bing.com");

	web_add_cookie("_RwBf=ilt=12&ihpd=0&ispd=1&rc=9&rb=9&gb=0&rg=0&pc=9&mtu=0&rbb=0.0&g=0&cid=&clo=0&v=1&l=2024-10-25T07:00:00.0000000Z&lft=0001-01-01T00:00:00.0000000&aof=0&o=0&p=bingcopilotwaitlist&c=MY00IA&t=2152&s=2023-03-27T06:53:12.4705193+00:00&ts=2024-10-25T08:41:25.4110747+00:00&rwred=0&wls=2&lka=1&lkt=1&TH=&mta=0&e=n2OZgvxwMaujihK1-TjKbpY4U0woP_qjYewxt222rM6YGscc08L63c3DIIey5iL8sCai3oBrf80Fm6vBUoN7BQ&A=&mte=0&dci=2&wlb=2&aad=1&ard=0001-01-01T00:00:00.0000000&rwdbt=-62135539200&wle=2&ccp=2&rwflt="
		"-62135539200&cpt=0&rwaul2=0; DOMAIN=www.bing.com");

	web_url("ExpandedDomainsFilterGlobal.json_2", 
		"URL=https://{host_www_bing_com}/bloomfilterfiles/ExpandedDomainsFilterGlobal.json", 
		"Resource=0", 
		"RecContentType=application/json", 
		"Referer=", 
		"Snapshot=t4.inf", 
		"Mode=HTML", 
		EXTRARES, 
		"Url=https://{host_kit_fontawesome_com}/f5cbf3afb2.js", "Referer=http://localhost:9999/", ENDITEM, 
		"Url=http://{host_fonts_gstatic_com}/s/opensans/v40/memvYaGs126MiZpBA-UvWbX2vVnXBbObj2OVTS-muw.woff2", "Referer=http://{host_fonts_googleapis_com}/", ENDITEM, 
		"Url=http://{host_fonts_gstatic_com}/s/opensans/v40/memvYaGs126MiZpBA-UvWbX2vVnXBbObj2OVTSGmu1aB.woff2", "Referer=http://{host_fonts_googleapis_com}/", ENDITEM, 
		LAST);

	web_url("crx", 
		"URL=https://{host_edge_microsoft_com}/extensionwebstorebase/v1/crx?os=win&arch=x64&os_arch=x86_64&nacl_arch=x86-64&prod=edgecrx&prodchannel=&prodversion=130.0.2849.56&lang=en-US&acceptformat=crx3,puff&x=id%3Dajdpfmkffanmkhejnopjppegokpogffp%26v%3D3.17.0%26installedby%3Dinternal%26uc&x=id%3Djmjflgjpcpepeafmmgdpfkogkghcpiha%26v%3D1.2.1%26installedby%3Dother%26uc", 
		"Resource=0", 
		"RecContentType=text/xml", 
		"Referer=", 
		"Snapshot=t5.inf", 
		"Mode=HTML", 
		EXTRARES, 
		"Url=https://{host_cdn_jsdelivr_net}/npm/sweetalert2@11", "Referer=http://localhost:9999/", ENDITEM, 
		LAST);

	web_url("blocklist", 
		"URL=https://{host_edge_microsoft_com}/abusiveadblocking/api/v1/blocklist", 
		"Resource=0", 
		"RecContentType=application/json", 
		"Referer=", 
		"Snapshot=t6.inf", 
		"Mode=HTML", 
		EXTRARES, 
		"Url=http://{host_fonts_gstatic_com}/s/opensans/v40/memvYaGs126MiZpBA-UvWbX2vVnXBbObj2OVTSCmu1aB.woff2", "Referer=http://{host_fonts_googleapis_com}/", ENDITEM, 
		"Url=https://{host_cdn_jsdelivr_net}/npm/bootstrap-icons@1.7.2/font/bootstrap-icons.css", "Referer=http://localhost:9999/", ENDITEM, 
		LAST);

	web_url("-341398893248106636%7C-2257558592581803439", 
		"URL=https://{host_edge_microsoft_com}/autofillservice/core/page/3693946771987704403/-341398893248106636%7C-2257558592581803439", 
		"Resource=0", 
		"RecContentType=application/json", 
		"Referer=", 
		"Snapshot=t7.inf", 
		"Mode=HTML", 
		EXTRARES, 
		"Url=https://{host_ka-f_fontawesome_com}/releases/v6.6.0/css/free.min.css?token=f5cbf3afb2", "Referer=http://localhost:9999/", ENDITEM, 
		"Url=https://{host_ka-f_fontawesome_com}/releases/v6.6.0/css/free-v5-font-face.min.css?token=f5cbf3afb2", "Referer=http://localhost:9999/", ENDITEM, 
		"Url=https://{host_ka-f_fontawesome_com}/releases/v6.6.0/css/free-v4-font-face.min.css?token=f5cbf3afb2", "Referer=http://localhost:9999/", ENDITEM, 
		"Url=https://{host_cdn_jsdelivr_net}/npm/bootstrap-icons@1.7.2/font/fonts/bootstrap-icons.woff2?30af91bf14e37666a085fb8a161ff36d", "Referer=https://{host_cdn_jsdelivr_net}/npm/bootstrap-icons@1.7.2/font/bootstrap-icons.css", ENDITEM, 
		LAST);

	web_url("shoppingsettings", 
		"URL=https://{host_www_bing_com}/api/shopping/v1/user/shoppingsettings", 
		"Resource=0", 
		"RecContentType=application/json", 
		"Referer=", 
		"Snapshot=t8.inf", 
		"Mode=HTML", 
		EXTRARES, 
		"Url=https://{host_ka-f_fontawesome_com}/releases/v6.6.0/css/free-v4-shims.min.css?token=f5cbf3afb2", "Referer=http://localhost:9999/", ENDITEM, 
		LAST);

	web_url("shoppingsettings_2", 
		"URL=https://{host_www_bing_com}/api/shopping/v1/user/shoppingsettings?EnabledServiceFeaturesv2=edgeServerUX.shopping.cashbackOfferDetails,edgeServerUX.shopping.cpsPdpCommerceNotificationOkVariant,edgeServerUX.shopping.msEdgeShoppingCashbackDismissTimeout2s", 
		"Resource=0", 
		"RecContentType=application/json", 
		"Referer=", 
		"Snapshot=t9.inf", 
		"Mode=HTML", 
		LAST);

	web_custom_request("rewardsUserInfo", 
		"URL=https://{host_www_bing_com}/api/shopping/v1/savings/rewards/rewardsUserInfo", 
		"Method=POST", 
		"Resource=0", 
		"RecContentType=application/json", 
		"Referer=", 
		"Snapshot=t10.inf", 
		"Mode=HTML", 
		"EncType=application/json", 
		"Body={\"IncludePromotions\":false,\"anid\":\"402C2EF147FEAA9E843EF5B1FFFFFFFF\",\"channel\":\"\"}", 
		LAST);

	web_url("-4276412816532324841%7C-2257558592581803439", 
		"URL=https://{host_edge_microsoft_com}/autofillservice/core/page/3693946771987704403/-4276412816532324841%7C-2257558592581803439", 
		"Resource=0", 
		"RecContentType=application/json", 
		"Referer=", 
		"Snapshot=t11.inf", 
		"Mode=HTML", 
		EXTRARES, 
		"Url=https://{host_ka-f_fontawesome_com}/releases/v6.6.0/webfonts/free-fa-brands-400.woff2", "Referer=http://localhost:9999/", ENDITEM, 
		LAST);

	web_url("selection", 
		"URL=https://{host_arc_msn_com}/v4/api/selection?placement=88000360&nct=1&fmt=json&ISU=1&AREF=0&APRIMB=0&ADEFAB=0&OPSYS=WIN10&locale=en-US&country=VN&edgeid=-945223331753601845&ACHANNEL=4&ABUILD=130.0.6723.70&poptin=1&devosver=10.0.22631.4317&clr=esdk&UITHEME=dark&EPCON=0&AMAJOR=130&AMINOR=0&ABLD=6723&APATCH=70", 
		"Resource=0", 
		"RecContentType=application/json", 
		"Referer=", 
		"Snapshot=t12.inf", 
		"Mode=HTML", 
		LAST);

	web_add_cookie("MUID=0FCCB47D65F0660E1085A5B8643067DB; DOMAIN=c.bing.com");

	web_add_cookie("SRM_B=0FCCB47D65F0660E1085A5B8643067DB; DOMAIN=c.bing.com");

	web_add_cookie("SRM_M=0FCCB47D65F0660E1085A5B8643067DB; DOMAIN=c.bing.com");

	web_add_cookie("msau=id=402C2EF147FEAA9E843EF5B1FFFFFFFF&msa=1&aad=0; DOMAIN=c.bing.com");

	web_add_cookie("CortanaAppUID=3ABB54AC5AFC2323AF83FB612BD036CB; DOMAIN=c.bing.com");

	web_add_cookie("_UR=QS=0&TQS=0; DOMAIN=c.bing.com");

	web_add_cookie("ANON=A=402C2EF147FEAA9E843EF5B1FFFFFFFF&E=1c1d&W=1; DOMAIN=c.bing.com");

	web_add_cookie("SRCHUSR=DOB=20220616&T=1699589618000; DOMAIN=c.bing.com");

	web_add_cookie("GI_FRE_COOKIE=gi_prompt=5&gi_fre=2&gi_sc=2; DOMAIN=c.bing.com");

	web_add_cookie("ABDEF=V=13&ABDV=13&MRNB=1711205721369&MRB=0; DOMAIN=c.bing.com");

	web_add_cookie("_tarLang=default=vi; DOMAIN=c.bing.com");

	web_add_cookie("_TTSS_OUT=hist=WyJlcyIsImVuIiwidmkiXQ==; DOMAIN=c.bing.com");

	web_add_cookie("WLS=C=&N=; DOMAIN=c.bing.com");

	web_add_cookie("SRCHD=AF=SHORUN; DOMAIN=c.bing.com");

	web_add_cookie("SRCHUID=V=2&GUID=AD2EDFE572EA4D0E9DC00BB4B9363813&dmnchg=1; DOMAIN=c.bing.com");

	web_add_cookie("_HPVN=CS=eyJQbiI6eyJDbiI6MTMsIlN0IjowLCJRcyI6MCwiUHJvZCI6IlAifSwiU2MiOnsiQ24iOjEzLCJTdCI6MCwiUXMiOjAsIlByb2QiOiJIIn0sIlF6Ijp7IkNuIjoxMywiU3QiOjAsIlFzIjowLCJQcm9kIjoiVCJ9LCJBcCI6dHJ1ZSwiTXV0ZSI6dHJ1ZSwiTGFkIjoiMjAyNC0wNi0yMVQwMDowMDowMFoiLCJJb3RkIjowLCJHd2IiOjAsIlRucyI6MCwiRGZ0IjpudWxsLCJNdnMiOjAsIkZsdCI6MCwiSW1wIjo0MiwiVG9ibiI6MH0=; DOMAIN=c.bing.com");

	web_add_cookie("EDGSRCHHPGUSR=CIBV=1.1805.0&udstone=Balanced&udstoneopts=h3imaginative,gencontentv3,fluxv1,flxegctxv3,egctxcplt,winsetv1; DOMAIN=c.bing.com");

	web_add_cookie("OIDR="
		"gxBcur1-f-E1pB9kHMehQtnsgjh2h-05VzDLDb_j_FgJvZ0hF_OedEu9iiPZtQWNcYECgpQJ3ZLxYRxFrDzHSgJnolUABIxO9BLy-tYhTr6Aih367S798mI2DxvtVPMtlNbLOkaIcTvtgrLADmU6eg8DM4YWRtw-Lu-QclCruGutqZKkvzbzO4coqtNvOLnsWETI7BpXEGc0o08leokSSgdnuT7CzLbZH3TtO6yCbIYW2IKKO2ELQr3KGNUHpe65XvzIjq3-q-nbNFgWzNUniXVcaoSwc0UmJZjWPOOTnjTox_24DYq2tffLy2UXsVsRjLpqW9Raduuh2e6O6nDOoVTGyw8auNkrQXtxqmvjgfERs0e6ctgvo2nu6Nuemyd1SFiwnefCD5fOE10KQslI8Ma7zZ1_AIZJxkb6STxvtMgUz73j_0VOWIurRdXehMU20TR-YXeqwXdu_A2N40W5E1GRPUL8SMbpmO2luQqIjc5pnEL7XQoPQS"
		"mB3QXK3b515ubNKcydmWh05sDYzt6wPjIqA_5FgaP17p3gmSOdRphAWoyQ7eaDRG3GMxcnjZ6bLqy2-wUR5gMzR5-DMrRlXhtgCgLJX5uCYHonlzfx9ujTKEtxrR7EPDz6qErP2glCn0_A5iTyBdkhbsgO61kANW-wzXWSYEVdfYIMwdUGKBPyGNXqvupzFvD1TIrs0iRh9ZWG_3wfPTNS2LRPWuQHlnrDspn9jK5HHK6X2Y9k7qJ1hJyZyJ4_pW7h2wdonZMCwD_rcH1xmDrj31EK2BcKLJvJT3gT-FTQeVQ86l7SFJ_IpW2DP-12W6wsNruKTQ9b0owImYQ1UWsks0Lh1wJzkySyQd9fHUPlNpliU3_olUec7VofJAyxJKN2TVeg1IUOxlSHDb6DTMXcMDxb5F0FbMamCPutZmvUCA8gJokAE7ygP8jrBpcrlgpnEtLYCGAplrRmMUSB2xJl3NuIc_y6PcU1YwkN_oaNJCOenEikwdRB"
		"C-Fv3ESpGvHzQ2bpp0nQs3bAx54uq896zjfa_B4mQhEPpcqPU1beXpl6QrwQUvbE3eP8QGIfE5gsugxPq-H8sZ9-bmxiY4zY_i0y9I_g9W7Id4vm-n5XzbfBVzLQwW-QXErEskuO68s-drdYlVmu8CO3de9u2Kwod9KTsVmbpSULwtc4lI9kq_B0-2lxeI5XSg; DOMAIN=c.bing.com");

	web_add_cookie("BFB=gxDIhWAKTlEgjWGvcuMJNqqDpJ4MsyQTLnFnrn8uzgbEgVaUPK-zio2jq2wk3cCNZgK9BC7pYAWyMK2e9CRCSyfcQ6mTHIdmaReUvmnvsXnVzYsMQeq-0hY2gpAz7GEvKda0BxR47dTnsLgfYHuRu7Oh0Yn8Ndq-_o73IoFX9Qv5ua-CZSTNUwsjaDQv0l_7em6u0Ymfx13r1wJiMdPR8gWsEmktFVV6VbYV3KBa5b0d0kRUgwxMU-77ujeafpqT36kK0CU-J0q51PQo1SKN-33y; DOMAIN=c.bing.com");

	web_add_cookie("USRLOC=HS=1&ELOC=LAT=21.016942977905273|LON=105.5311279296875|N=Tan%20Xa%20Commune%2C%20Hanoi|ELT=10|&BID=MjQxMDMwMDUxMTI5XzIyZWE5ZDg3OWQzZTFkYjg0ZDA5OTgwNjYyYTExYjQzYTY3OGY3YWVmOWZhM2ZjZjEwYmIxZTkwYTY2ZGZiNGQ=; DOMAIN=c.bing.com");

	web_add_cookie("_Rwho=u=d&ts=2024-10-25; DOMAIN=c.bing.com");

	web_add_cookie("dsc=order=BingPages; DOMAIN=c.bing.com");

	web_add_cookie("_RwBf=ilt=12&ihpd=0&ispd=1&rc=9&rb=9&gb=0&rg=0&pc=9&mtu=0&rbb=0.0&g=0&cid=&clo=0&v=1&l=2024-10-25T07:00:00.0000000Z&lft=0001-01-01T00:00:00.0000000&aof=0&o=0&p=bingcopilotwaitlist&c=MY00IA&t=2152&s=2023-03-27T06:53:12.4705193+00:00&ts=2024-10-25T08:41:25.4110747+00:00&rwred=0&wls=2&lka=1&lkt=1&TH=&mta=0&e=n2OZgvxwMaujihK1-TjKbpY4U0woP_qjYewxt222rM6YGscc08L63c3DIIey5iL8sCai3oBrf80Fm6vBUoN7BQ&A=&mte=0&dci=2&wlb=2&aad=1&ard=0001-01-01T00:00:00.0000000&rwdbt=-62135539200&wle=2&ccp=2&rwflt="
		"-62135539200&cpt=0&rwaul2=0; DOMAIN=c.bing.com");

	web_add_cookie("_SS=SID=05C65B54F56E6B84281E4A8AF45E6A76&PC=LCTS&R=9&RB=9&GB=0&RG=0&RP=9&OCID=MY02C1; DOMAIN=c.bing.com");

	web_add_cookie("OID=AxAWHcDc2NYVnZ-aT9kpO-TMuJy5iOmrflk_1vAC19LL2ln3YjpVTF9DuS9DtsgOXhvAyZGuW-H8_Vp5RqFGguDbEmhiHqu345slRM0KdIiskjhWfwpjwJE2uveoYAYaEAHj3QD8EhwHZRjNT4kU4SUC; DOMAIN=c.bing.com");

	web_add_cookie("OIDI=AxCMzOtx3Elq7tWbMjG4Y0EEv2IA2Xno7crhRQ-mbekFYg; DOMAIN=c.bing.com");

	web_add_cookie("MR=0; DOMAIN=c.bing.com");

	web_add_cookie("ipv6=hit=1730244356604&t=4; DOMAIN=c.bing.com");

	web_add_cookie("_TTSS_IN=hist=WyJqYSIsImtvIiwiZW4iLCJhdXRvLWRldGVjdCJd&isADRU=1; DOMAIN=c.bing.com");

	web_add_cookie("SRCHHPGUSR=SRCHLANG=en&BRW=W&BRH=M&CW=1385&CH=823&SW=1440&SH=900&DPR=2.0&UTC=420&DM=1&EXLTT=34&HV=1730240757&PV=15.0.0&PRVCW=1385&PRVCH=825&SCW=1370&SCH=3088&THEME=1&cdxtone=Precise&VCW=1366&VCH=771&WTS=63816462912&cdxtoneopts=,fluxprod&cdxwinturn=3&IG=F1D3E3878EB3497685756250A804A0D7&DESKDMOPTINTS=1695279257869&DESKDMOPTINCT=1&CIBV=1.1796.0&cdxupdttm=638545528343994943; DOMAIN=c.bing.com");

	web_url("config.json", 
		"URL=https://{host_edge-consumer-static_azureedge_net}/mouse-gesture/config.json", 
		"Resource=0", 
		"RecContentType=application/json", 
		"Referer=", 
		"Snapshot=t13.inf", 
		"Mode=HTML", 
		EXTRARES, 
		"Url=https://{host_c_bing_com}/c.gif?Red3=EdgPID_pd&pid=61d5220b-5ac4-4053-902c-e2920d81faad", "Referer=", ENDITEM, 
		LAST);

	web_url("-341398893248106636%7C-2257558592581803439%7C1720537294320060623", 
		"URL=https://{host_edge_microsoft_com}/autofillservice/core/page/3693946771987704403/-341398893248106636%7C-2257558592581803439%7C1720537294320060623", 
		"Resource=0", 
		"RecContentType=application/json", 
		"Referer=", 
		"Snapshot=t14.inf", 
		"Mode=HTML", 
		EXTRARES, 
		"Url=https://{host_ka-f_fontawesome_com}/releases/v6.6.0/webfonts/free-fa-solid-900.woff2", "Referer=http://localhost:9999/", ENDITEM, 
		"Url=https://{host_ka-f_fontawesome_com}/releases/v6.6.0/webfonts/free-fa-regular-400.woff2", "Referer=http://localhost:9999/", ENDITEM, 
		"Url=https://{host_file_hstatic_net}/200000020602/file/top-nhung-loai-hoa-dep-nhat__6__aba5ffa9c7324c1da0440565d915bb1d_grande.png", "Referer=http://localhost:9999/", ENDITEM, 
		LAST);

	web_url("-6030100249971701251%7C-7627269350225664860", 
		"URL=https://{host_edge_microsoft_com}/autofillservice/core/page/3693946771987704403/-6030100249971701251%7C-7627269350225664860", 
		"Resource=0", 
		"RecContentType=application/json", 
		"Referer=", 
		"Snapshot=t15.inf", 
		"Mode=HTML", 
		LAST);

	web_custom_request("update", 
		"URL=https://{host_edge_microsoft_com}/componentupdater/api/v1/update?cup2key=7:s84jWFQEE9fPWZ1DwDIZvFDVQnml7dSKnMw_49UuVOU&cup2hreq=8848d591e2f2131cd94d88ea29ea914b156b027a0b8c0a661bcb6cec4a37b08d", 
		"Method=POST", 
		"Resource=0", 
		"RecContentType=application/json", 
		"Referer=", 
		"Snapshot=t16.inf", 
		"Mode=HTML", 
		"EncType=application/json", 
		"Body={\"request\":{\"@os\":\"win\",\"@updater\":\"msedge\",\"acceptformat\":\"crx3\",\"app\":[{\"appid\":\"kpfehajjjbbcifeehjgfgnabifknmdad\",\"brand\":\"INBX\",\"cohort\":\"rrf@0.95\",\"enabled\":true,\"lang\":\"en-US\",\"packages\":{\"package\":[{\"fp\":\"1.42AF0D1905C8F1D8F6167365271C4549A73603B838BA58B9A664C57C00DB1EE5\"}]},\"ping\":{\"r\":-2},\"targetingattributes\":{\"AppCohort\":\"rrf@0.95\",\"AppMajorVersion\":\"130\",\"AppRollout\":0.95,\"AppVersion\":\"130.0.2849.56\",\"IsInternalUser\""
		":false,\"Priority\":false,\"Updater\":\"Omaha\",\"UpdaterVersion\":\"1.3.195.25\"},\"updatecheck\":{},\"version\":\"101.0.4906.0\"},{\"appid\":\"ndikpojcjlepofdkaaldkinkjbeeebkl\",\"brand\":\"INBX\",\"cohort\":\"rrf@0.81\",\"enabled\":true,\"lang\":\"en-US\",\"packages\":{\"package\":[{\"fp\":\"1.8657AD8DF1B23B55192C68D707CEBC7653AC24FBD8F4EABDA9F8954FF88F1634\"}]},\"ping\":{\"r\":-2},\"targetingattributes\":{\"AppCohort\":\"rrf@0.81\",\"AppMajorVersion\":\"130\",\"AppRollout\":0.81,\"AppVersion\""
		":\"130.0.2849.56\",\"IsInternalUser\":false,\"Priority\":false,\"Updater\":\"Omaha\",\"UpdaterVersion\":\"1.3.195.25\"},\"updatecheck\":{},\"version\":\"10.34.0.55\"},{\"appid\":\"ohckeflnhegojcjlcpbfpciadgikcohk\",\"brand\":\"INBX\",\"cohort\":\"rrf@0.36\",\"enabled\":true,\"lang\":\"en-US\",\"packages\":{\"package\":[{\"fp\":\"1.26123BEF7D73536450862D2C4D44963D720AA80B6FC2D8496F559CB9C1FDEB00\"}]},\"ping\":{\"r\":-2},\"targetingattributes\":{\"AppCohort\":\"rrf@0.36\",\"AppMajorVersion\":\"130\""
		",\"AppRollout\":0.36,\"AppVersion\":\"130.0.2849.56\",\"IsInternalUser\":false,\"Priority\":false,\"Updater\":\"Omaha\",\"UpdaterVersion\":\"1.3.195.25\"},\"updatecheck\":{},\"version\":\"0.0.1.4\"},{\"appid\":\"fgbafbciocncjfbbonhocjaohoknlaco\",\"brand\":\"INBX\",\"cohort\":\"rrf@0.07\",\"enabled\":true,\"installdate\":-1,\"lang\":\"en-US\",\"packages\":{\"package\":[{\"fp\":\"1.D551321488BC5B99465F9047C5EFED82B96666074E4C836AE7B5B91D1DEEE2E5\"}]},\"ping\":{\"r\":-2},\"targetingattributes\":{\""
		"AppCohort\":\"rrf@0.07\",\"AppMajorVersion\":\"130\",\"AppRollout\":0.07,\"AppVersion\":\"130.0.2849.56\",\"IsInternalUser\":false,\"Priority\":false,\"Updater\":\"Omaha\",\"UpdaterVersion\":\"1.3.195.25\"},\"updatecheck\":{},\"version\":\"2024.10.11.1\"},{\"appid\":\"oankkpibpaokgecfckkdkgaoafllipag\",\"brand\":\"INBX\",\"cohort\":\"rrf@0.84\",\"enabled\":true,\"lang\":\"en-US\",\"packages\":{\"package\":[{\"fp\":\"1.44C48B9ECD87ACDDD850F9AA5E1C9D48B7A398DEC13D376CD62D55DADBD464A5\"}]},\"ping\":"
		"{\"r\":-2},\"targetingattributes\":{\"AppCohort\":\"rrf@0.84\",\"AppMajorVersion\":\"130\",\"AppRollout\":0.84,\"AppVersion\":\"130.0.2849.56\",\"IsInternalUser\":false,\"Priority\":false,\"Updater\":\"Omaha\",\"UpdaterVersion\":\"1.3.195.25\"},\"updatecheck\":{},\"version\":\"6498.2023.8.1\"},{\"appid\":\"ahmaebgpfccdhgidjaidaoojjcijckba\",\"brand\":\"INBX\",\"cohort\":\"rrf@0.11\",\"enabled\":true,\"lang\":\"en-US\",\"ping\":{\"r\":-2},\"targetingattributes\":{\"AppCohort\":\"rrf@0.11\",\""
		"AppMajorVersion\":\"130\",\"AppRollout\":0.11,\"AppVersion\":\"130.0.2849.56\",\"IsInternalUser\":false,\"Priority\":false,\"Updater\":\"Omaha\",\"UpdaterVersion\":\"1.3.195.25\"},\"updatecheck\":{},\"version\":\"0.0.0.0\"},{\"appid\":\"pbdgbpmpeenomngainidcjmopnklimmf\",\"brand\":\"INBX\",\"cohort\":\"rrf@0.21\",\"enabled\":true,\"installdate\":-1,\"lang\":\"en-US\",\"packages\":{\"package\":[{\"fp\":\"1.CD01AC863490DA629313B1747627CB54E462DC5FA5C2C27FE1DA2E1B48BD1445\"}]},\"ping\":{\"r\":-2},\""
		"targetingattributes\":{\"AppCohort\":\"rrf@0.21\",\"AppMajorVersion\":\"130\",\"AppRollout\":0.21,\"AppVersion\":\"130.0.2849.56\",\"IsInternalUser\":false,\"Priority\":false,\"Updater\":\"Omaha\",\"UpdaterVersion\":\"1.3.195.25\"},\"updatecheck\":{},\"version\":\"0.0.0.37\"},{\"appid\":\"mpicjakjneaggahlnmbojhjpnileolnb\",\"brand\":\"INBX\",\"cohort\":\"rrf@0.74\",\"enabled\":true,\"lang\":\"en-US\",\"packages\":{\"package\":[{\"fp\":\""
		"1.CE76D55583A73A20436D496343E63405C4A75595400C1C87230986BDAA4BDB1A\"}]},\"ping\":{\"r\":-2},\"targetingattributes\":{\"AppCohort\":\"rrf@0.74\",\"AppMajorVersion\":\"130\",\"AppRollout\":0.74,\"AppVersion\":\"130.0.2849.56\",\"IsInternalUser\":false,\"Priority\":false,\"Updater\":\"Omaha\",\"UpdaterVersion\":\"1.3.195.25\"},\"updatecheck\":{},\"version\":\"4.0.1.5\"},{\"appid\":\"mkcgfaeepibomfapiapjaceihcojnphg\",\"brand\":\"INBX\",\"cohort\":\"rrf@0.76\",\"enabled\":true,\"lang\":\"en-US\",\""
		"ping\":{\"r\":-2},\"targetingattributes\":{\"AppCohort\":\"rrf@0.76\",\"AppMajorVersion\":\"130\",\"AppRollout\":0.76,\"AppVersion\":\"130.0.2849.56\",\"IsInternalUser\":false,\"Priority\":false,\"Updater\":\"Omaha\",\"UpdaterVersion\":\"1.3.195.25\"},\"updatecheck\":{},\"version\":\"0.0.0.0\"},{\"appid\":\"fppmbhmldokgmleojlplaaodlkibgikh\",\"brand\":\"INBX\",\"cohort\":\"rrf@0.05\",\"enabled\":true,\"lang\":\"en-US\",\"packages\":{\"package\":[{\"fp\":\""
		"1.A81D1959892AE4180554347DF1B97834ABBA2E1A5E6B9AEBA000ECEA26EABECC\"}]},\"ping\":{\"r\":-2},\"targetingattributes\":{\"AppCohort\":\"rrf@0.05\",\"AppMajorVersion\":\"130\",\"AppRollout\":0.05,\"AppVersion\":\"130.0.2849.56\",\"IsInternalUser\":false,\"Priority\":false,\"Updater\":\"Omaha\",\"UpdaterVersion\":\"1.3.195.25\"},\"updatecheck\":{},\"version\":\"1.15.0.1\"},{\"appid\":\"ebkkldgijmkljgglkajkjgedfnigiakk\",\"brand\":\"INBX\",\"cohort\":\"rrf@0.23\",\"enabled\":true,\"installdate\":-1,\""
		"lang\":\"en-US\",\"packages\":{\"package\":[{\"fp\":\"1.1B2BA8FC90BA68CD057B9CAAFFC218EAD59A23E37F79192ED37D0C3A7A8BAB03\"}]},\"ping\":{\"r\":-2},\"targetingattributes\":{\"AppCohort\":\"rrf@0.23\",\"AppMajorVersion\":\"130\",\"AppRollout\":0.23,\"AppVersion\":\"130.0.2849.56\",\"IsInternalUser\":false,\"Priority\":false,\"Updater\":\"Omaha\",\"UpdaterVersion\":\"1.3.195.25\"},\"updatecheck\":{},\"version\":\"1.0.0.20\"},{\"appid\":\"alpjnmnfbgfkmmpcfpejmmoebdndedno\",\"brand\":\"INBX\",\"cohort\""
		":\"rrf@0.41\",\"enabled\":true,\"installdate\":-1,\"lang\":\"en-US\",\"packages\":{\"package\":[{\"fp\":\"1.4AF7EE72E9C8E11DAB4124EB233B3B5771D0EE966ECE3055FB251667528D3D0D\"}]},\"ping\":{\"r\":-2},\"targetingattributes\":{\"AppCohort\":\"rrf@0.41\",\"AppMajorVersion\":\"130\",\"AppRollout\":0.41,\"AppVersion\":\"130.0.2849.56\",\"IsInternalUser\":false,\"Priority\":false,\"Updater\":\"Omaha\",\"UpdaterVersion\":\"1.3.195.25\"},\"updatecheck\":{},\"version\":\"14.0.0.1\"},{\"appid\":\""
		"lkkdlcloifjinapabfonaibjijloebfb\",\"brand\":\"INBX\",\"cohort\":\"rrf@0.55\",\"enabled\":true,\"lang\":\"en-US\",\"packages\":{\"package\":[{\"fp\":\"1.9062C8B926A08D9107CE98F8978AEB18DDEA7E9EB4306AA06D0B4203801C96FC\"}]},\"ping\":{\"r\":-2},\"targetingattributes\":{\"AppCohort\":\"rrf@0.55\",\"AppMajorVersion\":\"130\",\"AppRollout\":0.55,\"AppVersion\":\"130.0.2849.56\",\"IsInternalUser\":false,\"Priority\":false,\"Updater\":\"Omaha\",\"UpdaterVersion\":\"1.3.195.25\"},\"updatecheck\":{},\""
		"version\":\"4.0.3.4\"},{\"appid\":\"omnckhpgfmaoelhddliebabpgblmmnjp\",\"brand\":\"INBX\",\"cohort\":\"rrf@0.53\",\"enabled\":true,\"lang\":\"en-US\",\"packages\":{\"package\":[{\"fp\":\"1.DD91C7C496E4D9E8DF5BEAA3D33D45F9EF196B4F888D0FAC50EAF08CAD6B29D7\"}]},\"ping\":{\"r\":-2},\"targetingattributes\":{\"AppCohort\":\"rrf@0.53\",\"AppMajorVersion\":\"130\",\"AppRollout\":0.53,\"AppVersion\":\"130.0.2849.56\",\"IsInternalUser\":false,\"Priority\":false,\"Updater\":\"Omaha\",\"UpdaterVersion\":\""
		"1.3.195.25\"},\"updatecheck\":{},\"version\":\"1.0.0.2\"},{\"appid\":\"kmkacjgmmfchkbeglfbjjeidfckbnkca\",\"brand\":\"INBX\",\"cohort\":\"rrf@0.33\",\"enabled\":true,\"installdate\":-1,\"lang\":\"en-US\",\"packages\":{\"package\":[{\"fp\":\"1.4A84F2BDD63DABE6ABDE22B9047A6942EEB7BDF93D8435CC4B188DBE72D9E30D\"}]},\"ping\":{\"r\":-2},\"targetingattributes\":{\"AppCohort\":\"rrf@0.33\",\"AppMajorVersion\":\"130\",\"AppRollout\":0.33,\"AppVersion\":\"130.0.2849.56\",\"IsInternalUser\":false,\"Priority"
		"\":false,\"Updater\":\"Omaha\",\"UpdaterVersion\":\"1.3.195.25\"},\"updatecheck\":{},\"version\":\"2.0.0.0\"},{\"appid\":\"plbmmhnabegcabfbcejohgjpkamkddhn\",\"brand\":\"INBX\",\"cohort\":\"rrf@0.84\",\"enabled\":true,\"lang\":\"en-US\",\"packages\":{\"package\":[{\"fp\":\"1.D4BDD051480CF4B6034F47B4744FE4565BD08AABF9CD596E6E6F19458396753B\"}]},\"ping\":{\"r\":-2},\"targetingattributes\":{\"AppCohort\":\"rrf@0.84\",\"AppMajorVersion\":\"130\",\"AppRollout\":0.84,\"AppVersion\":\"130.0.2849.56\",\""
		"IsInternalUser\":false,\"Priority\":false,\"Updater\":\"Omaha\",\"UpdaterVersion\":\"1.3.195.25\"},\"updatecheck\":{},\"version\":\"3048\"},{\"appid\":\"hjaimielcgmceiphgjjfddlgjklfpdei\",\"brand\":\"INBX\",\"cohort\":\"rrf@0.80\",\"enabled\":true,\"installdate\":-1,\"lang\":\"en-US\",\"packages\":{\"package\":[{\"fp\":\"1.A00289AF85D31D698A0F6753B6CE67DBAB4BDFF639BDE5FC588A5D5D8A3885D5\"}]},\"ping\":{\"r\":-2},\"targetingattributes\":{\"AppCohort\":\"rrf@0.80\",\"AppMajorVersion\":\"130\",\""
		"AppRollout\":0.8,\"AppVersion\":\"130.0.2849.56\",\"IsInternalUser\":false,\"Priority\":false,\"Updater\":\"Omaha\",\"UpdaterVersion\":\"1.3.195.25\"},\"updatecheck\":{},\"version\":\"1.0.0.5\"},{\"appid\":\"jbfaflocpnkhbgcijpkiafdpbjkedane\",\"brand\":\"INBX\",\"cohort\":\"rrf@0.68\",\"enabled\":true,\"lang\":\"en-US\",\"ping\":{\"r\":-2},\"targetingattributes\":{\"AppCohort\":\"rrf@0.68\",\"AppMajorVersion\":\"130\",\"AppRollout\":0.68,\"AppVersion\":\"130.0.2849.56\",\"IsInternalUser\":false,\""
		"Priority\":false,\"Updater\":\"Omaha\",\"UpdaterVersion\":\"1.3.195.25\"},\"updatecheck\":{},\"version\":\"1.0.0.26\"},{\"appid\":\"eeobbhfgfagbclfofmgbdfoicabjdbkn\",\"brand\":\"INBX\",\"cohort\":\"rrf@0.78\",\"enabled\":true,\"lang\":\"en-US\",\"packages\":{\"package\":[{\"fp\":\"1.8BFD50D350D47445B57BB1D61BBDE41CEDA7AC43DC81FCE95BF1AC646D97D2A0\"}]},\"ping\":{\"r\":-2},\"targetingattributes\":{\"AppCohort\":\"rrf@0.78\",\"AppMajorVersion\":\"130\",\"AppRollout\":0.78,\"AppVersion\":\""
		"130.0.2849.56\",\"IsInternalUser\":false,\"Priority\":false,\"Updater\":\"Omaha\",\"UpdaterVersion\":\"1.3.195.25\"},\"updatecheck\":{},\"version\":\"1.0.0.8\"},{\"appid\":\"lfmeghnikdkbonehgjihjebgioakijgn\",\"brand\":\"INBX\",\"cohort\":\"rrf@0.31\",\"enabled\":true,\"lang\":\"en-US\",\"packages\":{\"package\":[{\"fp\":\"1.A465925132B62528E5B196DBEF0BAAE8B66F1BB6B85476A1ABCD3CECDA0B057C\"}]},\"ping\":{\"r\":-2},\"targetingattributes\":{\"AppCohort\":\"rrf@0.31\",\"AppMajorVersion\":\"130\",\""
		"AppRollout\":0.31,\"AppVersion\":\"130.0.2849.56\",\"IsInternalUser\":false,\"Priority\":false,\"Updater\":\"Omaha\",\"UpdaterVersion\":\"1.3.195.25\"},\"updatecheck\":{},\"version\":\"2.0.0.17\"},{\"appid\":\"ojblfafjmiikbkepnnolpgbbhejhlcim\",\"brand\":\"INBX\",\"cohort\":\"rrf@0.10\",\"enabled\":true,\"lang\":\"en-US\",\"packages\":{\"package\":[{\"fp\":\"1.C1E0755E98DF77F5A56556098D6898E27C5295377F6F0703EF98DB199920CDB5\"}]},\"ping\":{\"r\":-2},\"targetingattributes\":{\"AppCohort\":\""
		"rrf@0.10\",\"AppMajorVersion\":\"130\",\"AppRollout\":0.1,\"AppVersion\":\"130.0.2849.56\",\"IsInternalUser\":false,\"Priority\":false,\"Updater\":\"Omaha\",\"UpdaterVersion\":\"1.3.195.25\"},\"updatecheck\":{},\"version\":\"4.10.2830.1\"},{\"appid\":\"jcmcegpcehdchljeldgmmfbgcpnmgedo\",\"brand\":\"INBX\",\"cohort\":\"rrf@0.97\",\"enabled\":true,\"installdate\":-1,\"lang\":\"en-US\",\"packages\":{\"package\":[{\"fp\":\"1.730ADCCA2CE01FF5D0C3D1E0DA25E78B706E7AA52A15EDE6CC316E0B7AA8EE35\"}]},\"ping"
		"\":{\"r\":-2},\"targetingattributes\":{\"AppCohort\":\"rrf@0.97\",\"AppMajorVersion\":\"130\",\"AppRollout\":0.97,\"AppVersion\":\"130.0.2849.56\",\"IsInternalUser\":false,\"Priority\":false,\"Updater\":\"Omaha\",\"UpdaterVersion\":\"1.3.195.25\"},\"updatecheck\":{},\"version\":\"2024.10.31.1\"},{\"appid\":\"gllimckfbolmioaaihpppacjccghejen\",\"brand\":\"INBX\",\"cohort\":\"rrf@0.53\",\"enabled\":true,\"installdate\":-1,\"lang\":\"en-US\",\"packages\":{\"package\":[{\"fp\":\""
		"1.4466E240C25BBDD6278A46E9966A4C3C4D5DD64DAB897879FB479F158FAFAEC6\"}]},\"ping\":{\"r\":-2},\"targetingattributes\":{\"AppCohort\":\"rrf@0.53\",\"AppMajorVersion\":\"130\",\"AppRollout\":0.53,\"AppVersion\":\"130.0.2849.56\",\"IsInternalUser\":false,\"Priority\":false,\"Updater\":\"Omaha\",\"UpdaterVersion\":\"1.3.195.25\"},\"updatecheck\":{},\"version\":\"1.1.0.0\"},{\"appid\":\"llmidpclgepbgbgoecnhcmgfhmfplfao\",\"brand\":\"INBX\",\"cohort\":\"rrf@0.65\",\"enabled\":true,\"lang\":\"en-US\",\""
		"packages\":{\"package\":[{\"fp\":\"1.053B006D6C5DF2138AE97D7ED298A923204C32132FB2C75964F21043B770B00C\"}]},\"ping\":{\"r\":-2},\"targetingattributes\":{\"AppCohort\":\"rrf@0.65\",\"AppMajorVersion\":\"130\",\"AppRollout\":0.65,\"AppVersion\":\"130.0.2849.56\",\"IsInternalUser\":false,\"Priority\":false,\"Updater\":\"Omaha\",\"UpdaterVersion\":\"1.3.195.25\"},\"updatecheck\":{},\"version\":\"2.0.7813.0\"},{\"appid\":\"cllppcmmlnkggcmljjfigkcigaajjmid\",\"brand\":\"INBX\",\"cohort\":\"rrf@0.12\",\""
		"enabled\":true,\"installdate\":-1,\"lang\":\"en-US\",\"packages\":{\"package\":[{\"fp\":\"1.E20BC3D04EF53F0CE2C14DC6D2E51A6261632EF6B7B03F268B5CAB1393F20E70\"}]},\"ping\":{\"r\":-2},\"targetingattributes\":{\"AppCohort\":\"rrf@0.12\",\"AppMajorVersion\":\"130\",\"AppRollout\":0.12,\"AppVersion\":\"130.0.2849.56\",\"IsInternalUser\":false,\"Priority\":false,\"Updater\":\"Omaha\",\"UpdaterVersion\":\"1.3.195.25\"},\"updatecheck\":{},\"version\":\"128.18175.18169.515\"},{\"appid\":\""
		"pdfjdcjjjegpclfiilihfkmdfndkneei\",\"brand\":\"INBX\",\"cohort\":\"rrf@0.76\",\"enabled\":true,\"installdate\":-1,\"lang\":\"en-US\",\"packages\":{\"package\":[{\"fp\":\"1.D4F35ED48764E506556C6DCA40B4C73E77EB1BA4C37D3D9A3DE7ACEC117CCD5E\"}]},\"ping\":{\"r\":-2},\"targetingattributes\":{\"AppCohort\":\"rrf@0.76\",\"AppMajorVersion\":\"130\",\"AppRollout\":0.76,\"AppVersion\":\"130.0.2849.56\",\"IsInternalUser\":false,\"Priority\":false,\"Updater\":\"Omaha\",\"UpdaterVersion\":\"1.3.195.25\"},\""
		"updatecheck\":{},\"version\":\"2024.8.7.0\"}],\"arch\":\"x64\",\"dedup\":\"cr\",\"domainjoined\":true,\"hw\":{\"avx\":1,\"physmemory\":14,\"sse\":1,\"sse2\":1,\"sse3\":1,\"sse41\":1,\"sse42\":1,\"ssse3\":1},\"ismachine\":1,\"nacl_arch\":\"x86-64\",\"os\":{\"arch\":\"x86_64\",\"platform\":\"Windows\",\"version\":\"10.0.22631.4317\"},\"prodversion\":\"130.0.2849.56\",\"protocol\":\"3.1\",\"requestid\":\"{d835083d-5212-4e76-9237-5e2b85c22c5c}\",\"sessionid\":\"{7318d549-09c1-491c-9abe-63b454779344}\""
		",\"updater\":{\"autoupdatecheckenabled\":true,\"ismachine\":1,\"lastchecked\":0,\"laststarted\":0,\"name\":\"Omaha\",\"updatepolicy\":-1,\"version\":\"1.3.195.25\"},\"updaterversion\":\"130.0.2849.56\"}}", 
		LAST);

	web_custom_request("cloud_config_observers.json", 
		"URL=https://{host_static_edge_microsoftapp_net}/default/cloud_config_observers.json", 
		"Method=HEAD", 
		"Resource=0", 
		"RecContentType=application/json", 
		"Referer=", 
		"Snapshot=t17.inf", 
		"Mode=HTML", 
		EXTRARES, 
		"Url=https://{host_cdnjs_cloudflare_com}/ajax/libs/font-awesome/5.15.3/css/all.min.css", "Referer=http://localhost:9999/", ENDITEM, 
		"Url=https://{host_code_jquery_com}/jquery-3.5.1.min.js", "Referer=http://localhost:9999/", ENDITEM, 
		LAST);

	web_custom_request("1.0", 
		"URL=https://{host_functional_events_data_microsoft_com}/OneCollector/1.0/", 
		"Method=POST", 
		"Resource=0", 
		"RecContentType=application/json", 
		"Referer=", 
		"Snapshot=t18.inf", 
		"ContentEncoding=deflate", 
		"Mode=HTML", 
		"EncType=application/bond-compact-binary", 
		"Body=)4.0I=Microsoft.WebBrowser.Personalization.SAN.PreferenceSanConsentqÂ¯¨Ë—ôüÜ©\"o:70109aa3567b40e3bb8ac9e67a07b58aÑ‚„@Ë\niLENOVO‰82L7", 
		EXTRARES, 
		"Url=https://{host_stackpath_bootstrapcdn_com}/bootstrap/4.5.2/js/bootstrap.bundle.min.js", "Referer=http://localhost:9999/", ENDITEM, 
		"Url=https://{host_cdnjs_cloudflare_com}/ajax/libs/font-awesome/5.15.3/webfonts/fa-solid-900.woff2", "Referer=https://{host_cdnjs_cloudflare_com}/ajax/libs/font-awesome/5.15.3/css/all.min.css", ENDITEM, 
		"Url=https://{host_unpkg_com}/leaflet@1.4.0/dist/leaflet.js", "Referer=http://localhost:9999/", ENDITEM, 
		LAST);

	web_url("-6030100249971701251", 
		"URL=https://{host_edge_microsoft_com}/autofillservice/core/page/3693946771987704403/-6030100249971701251", 
		"Resource=0", 
		"RecContentType=application/json", 
		"Referer=", 
		"Snapshot=t19.inf", 
		"Mode=HTML", 
		LAST);

	web_custom_request("update_2", 
		"URL=https://{host_edge_microsoft_com}/componentupdater/api/v1/update", 
		"Method=POST", 
		"Resource=0", 
		"RecContentType=application/json", 
		"Referer=", 
		"Snapshot=t20.inf", 
		"Mode=HTML", 
		"EncType=application/json", 
		"Body={\"request\":{\"@os\":\"win\",\"@updater\":\"msedge\",\"acceptformat\":\"crx3\",\"app\":[{\"appid\":\"pdfjdcjjjegpclfiilihfkmdfndkneei\",\"brand\":\"INBX\",\"cohort\":\"rrf@0.76\",\"enabled\":true,\"event\":[{\"download_time_ms\":12027,\"downloaded\":6258,\"downloader\":\"bits\",\"eventresult\":1,\"eventtype\":14,\"nextversion\":\"2024.8.10.0\",\"previousversion\":\"2024.8.7.0\",\"total\":6258,\"url\":\"http://msedge.b.tlu.dl.delivery.mp.microsoft.com/filestreamingservice/files/"
		"bc35041b-0856-43ed-996f-3734dd62fdc1?P1=1730926795&P2=404&P3=2&P4=SfXVdNPMWRH2k%2fKpo%2fhUjeFp2D81614D%2fVDKhLkYx37QPpQHygmO7KkWaisJJqvmlLCRC%2fCnRu7TtZJwYi4OaA%3d%3d\"},{\"eventresult\":1,\"eventtype\":3,\"install_time_ms\":41,\"nextfp\":\"1.D4F35ED48764E506556C6DCA40B4C73E77EB1BA4C37D3D9A3DE7ACEC117CCD5E\",\"nextversion\":\"2024.8.10.0\",\"previousfp\":\"1.D4F35ED48764E506556C6DCA40B4C73E77EB1BA4C37D3D9A3DE7ACEC117CCD5E\",\"previousversion\":\"2024.8.7.0\"}],\"installdate\":-1,\"lang\":\"en-US\""
		",\"packages\":{\"package\":[{\"fp\":\"1.D4F35ED48764E506556C6DCA40B4C73E77EB1BA4C37D3D9A3DE7ACEC117CCD5E\"}]},\"version\":\"2024.8.10.0\"}],\"arch\":\"x64\",\"dedup\":\"cr\",\"domainjoined\":true,\"hw\":{\"avx\":1,\"physmemory\":14,\"sse\":1,\"sse2\":1,\"sse3\":1,\"sse41\":1,\"sse42\":1,\"ssse3\":1},\"ismachine\":1,\"nacl_arch\":\"x86-64\",\"os\":{\"arch\":\"x86_64\",\"platform\":\"Windows\",\"version\":\"10.0.22631.4317\"},\"prodversion\":\"130.0.2849.56\",\"protocol\":\"3.1\",\"requestid\":\""
		"{9e7e639e-7e26-4828-93be-9155548664e8}\",\"sessionid\":\"{7318d549-09c1-491c-9abe-63b454779344}\",\"updaterversion\":\"130.0.2849.56\"}}", 
		LAST);

	web_url("3517029987587817622", 
		"URL=https://{host_edge_microsoft_com}/autofillservice/core/page/3693946771987704403/3517029987587817622", 
		"Resource=0", 
		"RecContentType=application/json", 
		"Referer=", 
		"Snapshot=t21.inf", 
		"Mode=HTML", 
		EXTRARES, 
		"Url=https://{host_kit_fontawesome_com}/f5cbf3afb2.js", "Referer=http://localhost:9999/", ENDITEM, 
		LAST);

	return 0;
}