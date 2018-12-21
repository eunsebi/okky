package net.okjsp

import grails.plugin.springsecurity.SpringSecurityService

class MoneyController {

    SpringSecurityService springSecurityService

    def index() {

        User user = springSecurityService.loadCurrentUser()

        //println "id : " + user.getUsername()

        return [ pay: user.getUsername() ]

    }

    def payScheduleArticle(String smonth, String emonth) {

        String writer = "";

        if (req.getSession().getAttribute(CommonUtil.SESSION_USER) != null) {
            writer = (String) req.getSession().getAttribute(CommonUtil.SESSION_USER);
        } else {
            writer = email;
        }

        System.out.println("write : "  + writer);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("write", writer);
        paramMap.put("sDay", syear + "/" + smonth + "/01");
        paramMap.put("eDay", eyear + "/" + emonth + "/01");
        List<Map<String, String>> resultMap = homeService.getScheduleArticles(paramMap);

        return resultMap;
    }

    def payMonthSelect(String syear, String smonth) {
        User user = springSecurityService.loadCurrentUser()

        Map<String, String> paramMap = new HashMap<String, String>()
        Map<String, String> selectMap = new HashMap<String, String>()

        String writer = user.getUsername()

        //String writer = (String) request.getSession().getAttribute(CommonUtil.SESSION_USER);

        System.out.println ( "----------------- 급여정보 불러오기 ----------------------" );

        System.out.println ( "불러올 년도 : " + syear )
        System.out.println ( "불려올 달 : " + smonth )

        if ( Integer.parseInt ( smonth ) == 0 ) {
            // 12월 달력 불러올때 1년후 0월로 불러온다.
            System.out.println("------ 월이 0 이다 ----")
            smonth = "12"
            Integer yy = Integer.parseInt(syear) - 1
            syear = Integer.toString(yy)
            paramMap.put("payDate", syear + smonth)
        }

        if ( Integer.parseInt ( smonth ) <= 9 ) {
            System.out.println("9보다 작거나 같다")
            smonth = "0" + smonth
        } else if ( Integer.parseInt ( smonth ) <= 10 ) {
            System.out.println("10보다 작거나 같다")
            smonth = smonth
        }

        System.out.println ( "Smonth : " + smonth )
        paramMap.put ( "payDate", syear + smonth )
        paramMap.put ( "user_email", writer )

        //System.out.println("email : " + writer);
        System.out.println ( "불러오기 payDate : " + paramMap.get ( "payDate" ) )

        Map<String, ?> resultMap
        resultMap = homeService.payMonthSelect ( paramMap )

        System.out.println ( smonth + "월 급여정보 : " + resultMap )

        if ( resultMap == null ) {

            System.out.println("--------------------- 불러오기 급여가 Null 이다 -----------------")

            if (Integer.parseInt(smonth) == 1) {
                smonth = "12"
                Integer ass = Integer.parseInt(syear) - 1
                syear = Integer.toString( ass )
            } else {
                Integer aa = Integer.parseInt(smonth) - 1
                smonth = Integer.toString(aa)
            }

            System.out.println("불러올 급여년도 : " + syear)
            System.out.println("불러올 급여달 : " + smonth)

            if (Integer.parseInt(smonth) < 0) {
                System.out.println("작년 12월이다")
                Integer ccc = Integer.parseInt(syear) - 1
                syear = Integer.toString(ccc)
                smonth = "12"
                selectMap.put("pay_date", syear + smonth)
                System.out.println("12 월 넘었다 payDate : " + selectMap.get("pay_date"))
            }

            if (Integer.parseInt(smonth) <= 9) {
                System.out.println("9월 이후다~~")
                smonth = "0" + smonth
                paramMap.put("payDate", syear + smonth)
            } else {
                paramMap.put("payDate", syear + smonth)
            }

            System.out.println("------ if문 나왔다 smont : " + smonth)

            //paramMap.put("searchMonth", syear + "-" + smonth);
            System.out.println("한달전 계산 payDate : " + paramMap.get("payDate"))

            Map<String, String> selectResult = homeService.payMonthSelect(paramMap)

            System.out.println("한달전 급여정보 : " + selectResult)

            //selectMap.put("pay_date", selectResult.get("PAY_DATE"));
            selectMap.put("time_salary", selectResult.get("TIME_SALARY"))
            selectMap.put("job_time", selectResult.get("JOB_TIME"))
            selectMap.put("full_working_pension", selectResult.get("FULL_WORKING_PENSION"))
            selectMap.put("family_pension", selectResult.get("FAMILY_PENSION"))
            selectMap.put("texes", selectResult.get("TEXES"))
            selectMap.put("position_pension", selectResult.get("POSITION_PENSION"))
            selectMap.put("longevity_pension", selectResult.get("LONGEVITY_PENSION"))
            selectMap.put("yearly", selectResult.get("YEARLY"))
            selectMap.put("etc", selectResult.get("ETC"))
            selectMap.put("user_email", writer)

            if (selectResult != null) {

                System.out.println("selsectResult Null -------- smonth : " + smonth)

                if (Integer.parseInt(smonth) == 12) {
                    smonth = "1"
                    Integer abc = Integer.parseInt(syear) + 1
                    syear = Integer.toString(abc)
                } else {
                    Integer bb = Integer.parseInt(smonth) + 1
                    System.out.println("bb : " + bb)
                    smonth = Integer.toString(bb)
                }

                System.out.println("----- selectResult null ---- ")
                System.out.println("syear : " + syear)
                System.out.println("smonth : " + smonth)

                if (Integer.parseInt(smonth) <= 9) {
                    System.out.println("9월보다 작다")
                    smonth = "0" + smonth
                    selectMap.put("pay_date", syear + smonth)
                    System.out.println("9월 이전이다 payDate : " + selectMap.get("pay_date"))
                }

                if (Integer.parseInt(smonth) < 12) {
                    System.out.println("10월이후다");
                    System.out.println("smonth : " + smonth);
                    selectMap.put("pay_date", syear + smonth);
                    System.out.println("10월 이후다 payDate : " + selectMap.get("pay_date"));
                }

                if (Integer.parseInt(smonth) == 12) {
                    System.out.println("12월이다")
                    //Integer ccc = Integer.parseInt(syear) + 1
                    //syear = Integer.toString(ccc)
                    smonth = "12"
                    selectMap.put("pay_date", syear + smonth)
                    System.out.println("12 월이다 payDate : " + selectMap.get("pay_date"))
                }

                if (Integer.parseInt(smonth) < 0) {
                    System.out.println("작년 12월이다")
                    Integer ccc = Integer.parseInt(syear) - 1
                    syear = Integer.toString(ccc)
                    smonth = "12"
                    selectMap.put("pay_date", syear + smonth)
                    System.out.println("12 월 넘었다 payDate : " + selectResult.get("pay_date"))
                }

                System.out.println("-----------------------------------------------------------------------------")
                System.out.println("급여 등록 전 payDate : " + selectMap.get("pay_date"))
                selectMap.put("email", writer)

                Integer writeCnt = homeService.payMonthWrite(selectMap)

                System.out.println(selectMap.get("pay_date") + " 등록했다.")

                paramMap.put("payDate", selectMap.get("pay_date"))
                paramMap.put("user_email", writer)
                System.out.println("다시 검색할 년월 : " + paramMap.get("payDate"))
                resultMap = homeService.payMonthSelect(paramMap)

            }

        }

        System.out.println ( "다시 검색 결과 : " + resultMap )

        return resultMap
    }

    def scheduleWrite() {
        Map<String, String> paramMap = new HashMap<String, String>();
        Map<String, Object> resultMap = new HashMap<String, Object>();

        String writer = "";

        if (req.getSession().getAttribute(CommonUtil.SESSION_USER) != null) {
            writer = (String) req.getSession().getAttribute(CommonUtil.SESSION_USER);
        } else {
            writer = email;
        }
        //String writer = (String) req.getSession().getAttribute(CommonUtil.SESSION_USER);
        //StringTokenizer realname = new StringTokenizer(realnames, ",");
        //StringTokenizer subname = new StringTokenizer(subnames, ",");

        int seq = homeService.getScheduleMaxSeq();
        paramMap.put("seq", "" + (seq + 1));
        paramMap.put("writer", writer);
        paramMap.put("title", title);
        paramMap.put("contents", contents);
        paramMap.put("pay_day", pay_day);
        paramMap.put("pay_ot", pay_ot);
        paramMap.put("pay_ottime", pay_ottime);
        paramMap.put("pay_latetime", pay_latetime);
        paramMap.put("pay_nighttime", pay_nighttime);
        paramMap.put("starttime", starttime);
        paramMap.put("endtime", endtime);
        paramMap.put("etcYn", etcYn);
        resultMap.put("resultCnt", homeService.scheduleWrite(paramMap));

        /*while (realname.hasMoreElements() && subname.hasMoreElements()) {
            Map<String, String> fileMap = new HashMap<String, String>();
            String rname = realname.nextToken();
            String sname = subname.nextToken();
            fileMap.put("scheduleSeq", "" + (seq + 1));
            fileMap.put("realname", rname);
            fileMap.put("subname", sname);
            resultMap.put("fileCnt", homeService.scheduleFileWrite(fileMap));
        }*/
        return resultMap;
    }
}
