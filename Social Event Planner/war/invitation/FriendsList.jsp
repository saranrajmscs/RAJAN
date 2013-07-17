<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="javax.servlet.http.HttpSession" %>    
<%@ page import="org.json.JSONArray" %>
<%@ page import="org.json.JSONObject" %>
<%@ page import="java.util.*" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Social Event Planner</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <!-- Le styles -->
    <link href="../css/bootstrap.css" rel="stylesheet">
    <style type="text/css">
      body {
        padding-top: 20px;
        padding-bottom: 60px;
      }

      /* Custom container */
      .container {
        margin: 0 auto;
        max-width: 1000px;
      }
      .container > hr {
        margin: 60px 0;
      }

      /* Main marketing message and sign up button */
      .jumbotron {
        margin: 80px 0;
        text-align: center;
      }
      .jumbotron h1 {
        font-size: 100px;
        line-height: 1;
      }
      .jumbotron .lead {
        font-size: 24px;
        line-height: 1.25;
      }
      .jumbotron .btn {
        font-size: 21px;
        padding: 14px 24px;
      }

      /* Supporting marketing content */
      .marketing {
        margin: 60px 0;
      }
      .marketing p + h4 {
        margin-top: 28px;
      }


      /* Customize the navbar links to be fill the entire space of the .navbar */
      .navbar .navbar-inner {
        padding: 0;
      }
      .navbar .nav {
        margin: 0;
        display: table;
        width: 100%;
      }
      .navbar .nav li {
        display: table-cell;
        width: 1%;
        float: none;
      }
      .navbar .nav li a {
        font-weight: bold;
        text-align: center;
        border-left: 1px solid rgba(255,255,255,.75);
        border-right: 1px solid rgba(0,0,0,.1);
      }
      .navbar .nav li:first-child a {
        border-left: 0;
        border-radius: 3px 0 0 3px;
      }
      .navbar .nav li:last-child a {
        border-right: 0;
        border-radius: 0 3px 3px 0;
      }
    </style>
    

    <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
      <script src="../js/html5shiv.js"></script>
    <![endif]-->

    <!-- Fav and touch icons -->
    <link rel="apple-touch-icon-precomposed" sizes="144x144" href="../ico/apple-touch-icon-144-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="114x114" href="../ico/apple-touch-icon-114-precomposed.png">
      <link rel="apple-touch-icon-precomposed" sizes="72x72" href="../ico/apple-touch-icon-72-precomposed.png">
                    <link rel="apple-touch-icon-precomposed" href="../ico/apple-touch-icon-57-precomposed.png">
                                   <script >
    
    function continueNoRedirect() {
    	//alert("continueNoRedirect()");
    	var formObj = document.getElementById("pageForm");
    	formObj.submit();
    }
    
    function continueRedirect() {
    	//alert("continueRedirect");
    	var hidObj = document.getElementById("navigationControl");
    	//alert("hidObj.Value 1 " +hidObj.value);
    	
    	hidObj.value = "RedirectToGPlusFriends";
    	//alert("hidObj.Value " +hidObj.value);
    	
    	
    	var formObj = document.getElementById("pageForm");
    	//alert("Form.Value " +document.getElementById("navigationControl").value);
    	//alert("Form "+formObj);
    	formObj.submit();
    }
    </script>
  </head>
<%
String disableBut = request.getParameter("DisableButton");
disableBut = disableBut == null ? "" : disableBut.trim();
String userFirstName = (String) session.getAttribute("userFirstName");
%>
  <body>

    <div class="container">
	<%
    	if(userFirstName != null) {
    %>
		<li>Hello: <%=userFirstName %> (if not, <a href="/LogoutServlet">Logout</a>)</li>
	<% } %>
      <div class="masthead">
        <h2 class="muted">Social Event Planner</h2>
        <div class="navbar">
          <div class="navbar-inner">
            <div class="container">
              <ul class="nav">
                <li ><a href="../index.html">Home</a></li>
                <li><a href="/GoogleOpenIdHandler?method=signInMethod">Sign In</a></li>
                <li class="active"><a href="./CreateInvitationMain.jsp">Create Invitation</a></li>
                <!--  li><a href="#">Photo Album</a></li-->
                <li><a href="/RecommendGiftHandler?method=getEventsAndInvitees">Recommend Gift</a></li>
                <!-- li><a href="#">Contact</a></li-->
              </ul>
            </div>
          </div>
        </div><!-- /.navbar -->
      </div>

<form class="form-horizontal" action="/InvitationHandler" method="post" id="pageForm">
 <input type="hidden" name="HiddenControl" value="FriendSelect">
 <input type="hidden" id="navigationControl" value="Jesus" name="navigationControl">
 <div class="row-fluid">
 	<div class="span12"><strong>Please select your Facebook Friends for the Event from the below list :</strong></div>
 </div>

 <%
 
 	
	/*String str = "{\"id\":\"1225410329\",\"name\":\"Saravanarajan Venkatachalam\",\"friends\":{\"data\":[{\"name\":\"Tiffany Yong\",\"id\":\"2512840\"},{\"name\":\"Vijay Panati\",\"id\":\"3423806\"},{\"name\":\"Arnab Banerjee\",\"id\":\"502253476\"},{\"name\":\"Beatrice Preethi\",\"id\":\"505212755\"},{\"name\":\"Cesar Sanchez\",\"id\":\"523487992\"},{\"name\":\"Harini Rajagopalan\",\"id\":\"531960901\"},{\"name\":\"Anand Ayyaswamy\",\"id\":\"550210815\"},{\"name\":\"Chintan Bhatt\",\"id\":\"553082843\"},{\"name\":\"Ramesh Lakshmanan\",\"id\":\"554747432\"},{\"name\":\"Biswajit Das\",\"id\":\"555258525\"},{\"name\":\"Christopher L Heuertz\",\"id\":\"569785430\"},{\"name\":\"Indranil Ghosh\",\"id\":\"570206482\"},{\"name\":\"Pushkar Bharadwaj\",\"id\":\"575938895\"},{\"name\":\"Ashok Rajanna\",\"id\":\"576907442\"},{\"name\":\"Amitava Dutta\",\"id\":\"582257645\"},{\"name\":\"Kathy Gray\",\"id\":\"584061897\"},{\"name\":\"Sudip Mazumder\",\"id\":\"584417352\"},{\"name\":\"Angela Olivas\",\"id\":\"588537243\"},{\"name\":\"Nazreen Jeelan\",\"id\":\"590778485\"},{\"name\":\"Arunkumar Sivanesan\",\"id\":\"592745101\"},{\"name\":\"Rekha Ravi\",\"id\":\"595289677\"},{\"name\":\"Tejas Oza\",\"id\":\"596526615\"},{\"name\":\"Ken Lee\",\"id\":\"601367109\"},{\"name\":\"Anusha Verma\",\"id\":\"602097845\"},{\"name\":\"Muthukumar Lakshminarayanan\",\"id\":\"604914911\"},{\"name\":\"Manish Kumar Agrawal\",\"id\":\"609314677\"},{\"name\":\"Ashok Kumar\",\"id\":\"611098460\"},{\"name\":\"Vijayarengan Ramanuja Chary\",\"id\":\"612502467\"},{\"name\":\"Jeelan Syed\",\"id\":\"613865885\"},{\"name\":\"Surajit Das\",\"id\":\"622269512\"},{\"name\":\"Sathish Ram\",\"id\":\"628469284\"},{\"name\":\"Ravi Kant Singh\",\"id\":\"629892556\"},{\"name\":\"Ravi Guntupally\",\"id\":\"632272806\"},{\"name\":\"Aziz Shaikh Abdul\",\"id\":\"632536143\"},{\"name\":\"Santosh Brahmachary\",\"id\":\"644077904\"},{\"name\":\"Prasannaa Shanthi Sadhasivvan\",\"id\":\"652463157\"},{\"name\":\"Kavitha Kuppannan\",\"id\":\"661976752\"},{\"name\":\"Sumita Chandran\",\"id\":\"663824921\"},{\"name\":\"Sumaya Shakir\",\"id\":\"670152753\"},{\"name\":\"Manoj Anand Nityanandam\",\"id\":\"672381864\"},{\"name\":\"Ukp Parida\",\"id\":\"676364565\"},{\"name\":\"Naveen Reddy\",\"id\":\"678894438\"},{\"name\":\"Bala Chidambaram\",\"id\":\"679490878\"},{\"name\":\"Annadurai Narayanan\",\"id\":\"679494459\"},{\"name\":\"Panjanathan Arunachalam\",\"id\":\"687363570\"},{\"name\":\"Changa Reddy\",\"id\":\"688608922\"},{\"name\":\"Nabarun Saha\",\"id\":\"690612222\"},{\"name\":\"Bala Raman\",\"id\":\"691102887\"},{\"name\":\"Sanjaya Sahoo\",\"id\":\"691902551\"},{\"name\":\"Anil Miyapuram\",\"id\":\"692166669\"},{\"name\":\"N\u00e3nda K\u00edshore\",\"id\":\"695698201\"},{\"name\":\"Hanson Yeung\",\"id\":\"697159288\"},{\"name\":\"Iftekar Ahmed\",\"id\":\"699290801\"},{\"name\":\"Rajendra Damaraju\",\"id\":\"703423537\"},{\"name\":\"Mohit Mehrotra\",\"id\":\"704262228\"},{\"name\":\"Rajib Das\",\"id\":\"705963854\"},{\"name\":\"Sayee Abin\",\"id\":\"707590115\"},{\"name\":\"Ram Kadiyala\",\"id\":\"710827442\"},{\"name\":\"Paul Sugiura\",\"id\":\"712878427\"},{\"name\":\"David Hilson\",\"id\":\"715437303\"},{\"name\":\"Arunava Sen\",\"id\":\"718024465\"},{\"name\":\"Arun Shunmugam\",\"id\":\"721707914\"},{\"name\":\"Sai Anand\",\"id\":\"724755972\"},{\"name\":\"Vidhyasankar Natarajan\",\"id\":\"726723411\"},{\"name\":\"Bhushan Sharma\",\"id\":\"732445090\"},{\"name\":\"Periyannaswamy Durairaj\",\"id\":\"735714018\"},{\"name\":\"Venugopal Chandran\",\"id\":\"739143511\"},{\"name\":\"Dipankar Biswas\",\"id\":\"740309285\"},{\"name\":\"Shamit Mandal\",\"id\":\"766058919\"},{\"name\":\"Arijit Chaudhuri\",\"id\":\"770653467\"},{\"name\":\"Archana Mahabaleshwarkar\",\"id\":\"773858476\"},{\"name\":\"Senthilkumar Thamilarasan\",\"id\":\"778049667\"},{\"name\":\"Anand Dhandapani\",\"id\":\"778897854\"},{\"name\":\"Ravi Varma\",\"id\":\"779003321\"},{\"name\":\"Altaf Hakim\",\"id\":\"779928023\"},{\"name\":\"Anindya Sanyal\",\"id\":\"780075821\"},{\"name\":\"Sourav Chatterjee\",\"id\":\"785275347\"},{\"name\":\"Shibashis Mohanty\",\"id\":\"791349372\"},{\"name\":\"Dushanthie Jayamaha\",\"id\":\"798582570\"},{\"name\":\"Jagadeesh Duraisamy\",\"id\":\"802828568\"},{\"name\":\"Rajagopalan Trichy\",\"id\":\"810264590\"},{\"name\":\"Rishi Mehta\",\"id\":\"820605292\"},{\"name\":\"Alex F Raja\",\"id\":\"903780226\"},{\"name\":\"Sheetal Vadaga\",\"id\":\"1002110764\"},{\"name\":\"Niket Peshwe\",\"id\":\"1011082122\"},{\"name\":\"Dhanalakshmi Annadurai\",\"id\":\"1011273660\"},{\"name\":\"Venkat Raman\",\"id\":\"1012105745\"},{\"name\":\"Mangesh Gandhe\",\"id\":\"1013917246\"},{\"name\":\"Senjuti Mukherjee\",\"id\":\"1021232340\"},{\"name\":\"Kalpana Nagarajan\",\"id\":\"1037174779\"},{\"name\":\"Sudhakar Nagireddy\",\"id\":\"1037317821\"},{\"name\":\"Lala Viswanath\",\"id\":\"1040404014\"},{\"name\":\"Rajib Ghosh\",\"id\":\"1048095193\"},{\"name\":\"Shivananda Patro\",\"id\":\"1049966348\"},{\"name\":\"Kamal Boovaragavan\",\"id\":\"1054098505\"},{\"name\":\"Divakar Nayak\",\"id\":\"1056750346\"},{\"name\":\"Senthil Kumar\",\"id\":\"1065974309\"},{\"name\":\"Anjali Rangwani\",\"id\":\"1068310369\"},{\"name\":\"Senthil Kumar Murugesan\",\"id\":\"1075925705\"},{\"name\":\"Shankar Lingam\",\"id\":\"1076651859\"},{\"name\":\"Swapnil Deshpande\",\"id\":\"1096885765\"},{\"name\":\"Uma Chandran\",\"id\":\"1096967322\"},{\"name\":\"Pratyush Panda\",\"id\":\"1106710437\"},{\"name\":\"Murali Krishna Siruvuru\",\"id\":\"1111195810\"},{\"name\":\"Ansuman Sircar\",\"id\":\"1116290958\"},{\"name\":\"Mouleeswaran Kumaresan\",\"id\":\"1122084393\"},{\"name\":\"Saravana Kumar\",\"id\":\"1123291180\"},{\"name\":\"Guna Babu\",\"id\":\"1142372485\"},{\"name\":\"Naga Rajan\",\"id\":\"1156530386\"},{\"name\":\"Nimmi Pushparajan\",\"id\":\"1162786378\"},{\"name\":\"Thangaraj Dhanasekaran\",\"id\":\"1163083259\"},{\"name\":\"Ganesh Kumar\",\"id\":\"1184338211\"},{\"name\":\"Muthuselvan Chandran\",\"id\":\"1198231349\"},{\"name\":\"Mohan Kumar\",\"id\":\"1223986197\"},{\"name\":\"Ameena Ismail\",\"id\":\"1227507615\"},{\"name\":\"Nikhil Bhangaonkar\",\"id\":\"1240343430\"},{\"name\":\"Praveen Dhadoti\",\"id\":\"1246476517\"},{\"name\":\"Neelanjan Sinha\",\"id\":\"1250889883\"},{\"name\":\"Shaik Dawood\",\"id\":\"1255899530\"},{\"name\":\"Nancy Apolonio\",\"id\":\"1257812526\"},{\"name\":\"Aruna Yogaraj\",\"id\":\"1276010510\"},{\"name\":\"Avijit Nayek\",\"id\":\"1278141833\"},{\"name\":\"Pradyumna Satapathy\",\"id\":\"1292894116\"},{\"name\":\"Sangeetha Sundaram\",\"id\":\"1298853979\"},{\"name\":\"Chandan Bhar\",\"id\":\"1305670847\"},{\"name\":\"Anita Salgado\",\"id\":\"1314114948\"},{\"name\":\"Saikrishna Parvathaneni\",\"id\":\"1317044560\"},{\"name\":\"R Rooban Annamalai\",\"id\":\"1319103061\"},{\"name\":\"Arijit Pal\",\"id\":\"1319829121\"},{\"name\":\"Srinivasa Rao Upputuri\",\"id\":\"1323807327\"},{\"name\":\"Umasanker Shunmughom\",\"id\":\"1324195090\"},{\"name\":\"Mathu Viji\",\"id\":\"1326109937\"},{\"name\":\"Vijay Kumar\",\"id\":\"1327268795\"},{\"name\":\"Subu Tangirala\",\"id\":\"1333843329\"},{\"name\":\"Navneet Gaur\",\"id\":\"1337158159\"},{\"name\":\"Anuop Udayakumar\",\"id\":\"1348309428\"},{\"name\":\"Swami Vaithyanathan\",\"id\":\"1356126973\"},{\"name\":\"Purnendu Sarkar\",\"id\":\"1357767517\"},{\"name\":\"Karthikeyan Gurumoorthy\",\"id\":\"1381585967\"},{\"name\":\"Murari Krishnamachari\",\"id\":\"1384985346\"},{\"name\":\"Srinivasan Sankaranarayanan\",\"id\":\"1386165965\"},{\"name\":\"Saravanakumar Gnanasambandam\",\"id\":\"1391607028\"},{\"name\":\"Ranjith Appala\",\"id\":\"1399004234\"},{\"name\":\"Sunayana Ghosh Roy\",\"id\":\"1401728334\"},{\"name\":\"Debajyoti Karmakar\",\"id\":\"1403449516\"},{\"name\":\"Harsh Bembi\",\"id\":\"1412093372\"},{\"name\":\"Ayan Sinha\",\"id\":\"1425092242\"},{\"name\":\"Sreevidhya Sankaran\",\"id\":\"1425481721\"},{\"name\":\"Sajith Kumar\",\"id\":\"1428443045\"},{\"name\":\"Hari Bramman\",\"id\":\"1430259757\"},{\"name\":\"Muralidharan Balakrishnan\",\"id\":\"1433861485\"},{\"name\":\"Saravanan Ramasamy\",\"id\":\"1457360430\"},{\"name\":\"Vishnu Agrawal\",\"id\":\"1457599768\"},{\"name\":\"Arindam Patranabis\",\"id\":\"1467283256\"},{\"name\":\"Shiddhu Pattanashettar\",\"id\":\"1483486893\"},{\"name\":\"Vamsi Madiraju\",\"id\":\"1486081551\"},{\"name\":\"Balagangatharathilak Sivagnanam\",\"id\":\"1486873915\"},{\"name\":\"Joon Kim\",\"id\":\"1502511021\"},{\"name\":\"Bently Prabhahar\",\"id\":\"1511272259\"},{\"name\":\"Meganathan Chinnaraj\",\"id\":\"1515172192\"},{\"name\":\"Shruthi Sampathkumar\",\"id\":\"1518013917\"},{\"name\":\"Kums Sandran\",\"id\":\"1519097605\"},{\"name\":\"Sathya Prakash\",\"id\":\"1539736912\"},{\"name\":\"Kumaran Ramasamy\",\"id\":\"1548526449\"},{\"name\":\"Vijayakumar T Subramanian\",\"id\":\"1565342071\"},{\"name\":\"Sasikumar Kondalraj\",\"id\":\"1566641728\"},{\"name\":\"Amit Pilliwar\",\"id\":\"1591778408\"},{\"name\":\"Amit Sinha\",\"id\":\"1595161714\"},{\"name\":\"Bruce Siebs\",\"id\":\"1602217833\"},{\"name\":\"Nagarajan Sridharan\",\"id\":\"1629144155\"},{\"name\":\"Sreekesh Nair\",\"id\":\"1643396578\"},{\"name\":\"Srinivasa Kilaru\",\"id\":\"1645442326\"},{\"name\":\"Vani Peddineni\",\"id\":\"1659024475\"},{\"name\":\"Edita Gatdula\",\"id\":\"1666602607\"},{\"name\":\"Ravikumar Chinnaswamy\",\"id\":\"1704542339\"},{\"name\":\"Ram Ganti\",\"id\":\"1711891454\"},{\"name\":\"Subash Maddala\",\"id\":\"1718066199\"},{\"name\":\"Srinivas Kammari\",\"id\":\"1727623696\"},{\"name\":\"Revathi Jayaraman\",\"id\":\"1729594298\"},{\"name\":\"Nagasai Ganduri\",\"id\":\"1746028723\"},{\"name\":\"Kumaresh Kuttisamy\",\"id\":\"1753867203\"},{\"name\":\"Sadheesh Venkatachalam\",\"id\":\"1770337673\"},{\"name\":\"Ahamed Ali\",\"id\":\"1787042130\"},{\"name\":\"Chandramohan Thirunagu\",\"id\":\"1816337543\"},{\"name\":\"Srivardhan Reddy\",\"id\":\"1816730771\"},{\"name\":\"Ashis Basak\",\"id\":\"1833761167\"},{\"name\":\"Vishal Jain\",\"id\":\"1835126829\"},{\"name\":\"Priyadarsini Sarkar Dutta\",\"id\":\"1837256258\"},{\"name\":\"Elayaraja Rajamanickam\",\"id\":\"100000007114410\"},{\"name\":\"Vinodkumar Narayanan\",\"id\":\"100000018127048\"},{\"name\":\"Karthik Venkatachalam\",\"id\":\"100000024661023\"},{\"name\":\"Biswadeep Pati\",\"id\":\"100000030065492\"},{\"name\":\"Mahadevan Vijayagopal\",\"id\":\"100000031149088\"},{\"name\":\"Saravanakumar Masilamani\",\"id\":\"100000044447708\"},{\"name\":\"Ilavarasi Senthilkumar\",\"id\":\"100000046081803\"},{\"name\":\"Koushik Chakrabarti\",\"id\":\"100000049737542\"},{\"name\":\"Sowmya Gopal\",\"id\":\"100000053722179\"},{\"name\":\"Rajesh Shanmugasundaram\",\"id\":\"100000066838996\"},{\"name\":\"Vijay Pendem\",\"id\":\"100000067845813\"},{\"name\":\"Vijayakumar Jayabalu\",\"id\":\"100000099685564\"},{\"name\":\"Durgaprasad Sreeramoju\",\"id\":\"100000114327219\"},{\"name\":\"Selvam Krishnan\",\"id\":\"100000119117826\"},{\"name\":\"Vijay Krishnan\",\"id\":\"100000139206376\"},{\"name\":\"Maria Kemp\",\"id\":\"100000141642935\"},{\"name\":\"Ganesh Sivakumar\",\"id\":\"100000161985856\"},{\"name\":\"Chithra Satheesh\",\"id\":\"100000171108905\"},{\"name\":\"Arun Prakkash\",\"id\":\"100000191747398\"},{\"name\":\"Sasi Kanth\",\"id\":\"100000191958380\"},{\"name\":\"Stella George\",\"id\":\"100000210000211\"},{\"name\":\"Manas Choudhury\",\"id\":\"100000220497160\"},{\"name\":\"Lakshmikanth Pandre\",\"id\":\"100000226447791\"},{\"name\":\"Debayan Bhattacharya\",\"id\":\"100000226596451\"},{\"name\":\"Siva Kumar\",\"id\":\"100000227800776\"},{\"name\":\"Rishi Raizada\",\"id\":\"100000229274224\"},{\"name\":\"Chowdary Nelavalli\",\"id\":\"100000273440517\"},{\"name\":\"KC Rajkumar\",\"id\":\"100000295715295\"},{\"name\":\"Santhosh Kumar\",\"id\":\"100000301720207\"},{\"name\":\"Shanmugarajan Balasubramani\",\"id\":\"100000341086425\"},{\"name\":\"Mahes Waran\",\"id\":\"100000342812244\"},{\"name\":\"Mani Vannan\",\"id\":\"100000351318259\"},{\"name\":\"Revathi Jayaraman\",\"id\":\"100000361774091\"},{\"name\":\"Gokul Chandran\",\"id\":\"100000376027003\"},{\"name\":\"Swaminathan Srinivasan\",\"id\":\"100000399022576\"},{\"name\":\"Amitava Dutta\",\"id\":\"100000402874619\"},{\"name\":\"Chandrani Shome\",\"id\":\"100000410778304\"},{\"name\":\"Mohamed Yasin\",\"id\":\"100000419484727\"},{\"name\":\"Arunkumar Varadhan\",\"id\":\"100000426133740\"},{\"name\":\"Arijit Sen\",\"id\":\"100000440680454\"},{\"name\":\"Atanu Roychoudhury\",\"id\":\"100000447288136\"},{\"name\":\"Joydip Maitra\",\"id\":\"100000452508244\"},{\"name\":\"Sharanya Ashwin\",\"id\":\"100000464886816\"},{\"name\":\"Avijit Acharya\",\"id\":\"100000510005213\"},{\"name\":\"Subba Rao Challagolla\",\"id\":\"100000522071818\"},{\"name\":\"Poonam Bisht Agrawal\",\"id\":\"100000531141901\"},{\"name\":\"Manicandan Sai\",\"id\":\"100000600061640\"},{\"name\":\"Ramprabu Kalidass\",\"id\":\"100000619629871\"},{\"name\":\"Suria Prakashh\",\"id\":\"100000626749186\"},{\"name\":\"Ganapathy Malleeswar\",\"id\":\"100000628646159\"},{\"name\":\"Santi Manivasagam\",\"id\":\"100000640131056\"},{\"name\":\"Vijay Anand\",\"id\":\"100000646804010\"},{\"name\":\"Sundar Swaminathan\",\"id\":\"100000647121952\"},{\"name\":\"Smarty Barani\",\"id\":\"100000663535955\"},{\"name\":\"Rajendran Duraiswamy\",\"id\":\"100000663913538\"},{\"name\":\"Senthil Kanagaraju\",\"id\":\"100000676565140\"},{\"name\":\"Lalitha Ramanathan\",\"id\":\"100000686073369\"},{\"name\":\"Kiran Reddy\",\"id\":\"100000698315068\"},{\"name\":\"Shiva Janga Reddy Munugala\",\"id\":\"100000701656233\"},{\"name\":\"Bala Jeyarajan\",\"id\":\"100000702705085\"},{\"name\":\"Venkatesh Gurusamy\",\"id\":\"100000726739413\"},{\"name\":\"Vadivel Prakash\",\"id\":\"100000732799675\"},{\"name\":\"Madhumathy Venkatraman\",\"id\":\"100000743246979\"},{\"name\":\"Siva Prasath Ponnusamy\",\"id\":\"100000772994028\"},{\"name\":\"Mohammed Syed\",\"id\":\"100000780937336\"},{\"name\":\"Tamil Selvan Govindasamy\",\"id\":\"100000789385712\"},{\"name\":\"Shanmuga Kandaswamy\",\"id\":\"100000789633047\"},{\"name\":\"Hrudananda Patra\",\"id\":\"100000818611838\"},{\"name\":\"Roohul Pma\",\"id\":\"100000826016279\"},{\"name\":\"Sadasivam Gopalan\",\"id\":\"100000830252174\"},{\"name\":\"Aloha Kop\",\"id\":\"100000837780908\"},{\"name\":\"Amit Mitra\",\"id\":\"100000928794311\"},{\"name\":\"Senthil Cumar\",\"id\":\"100000946415762\"},{\"name\":\"Arun Gk\",\"id\":\"100000956986149\"},{\"name\":\"Manish Gupta\",\"id\":\"100000968596181\"},{\"name\":\"Manju Singh\",\"id\":\"100000995326367\"},{\"name\":\"Nelson Vivek\",\"id\":\"100001039854063\"},{\"name\":\"Senthilkumar Kannan\",\"id\":\"100001049010052\"},{\"name\":\"Krishnan Subramanian\",\"id\":\"100001110315912\"},{\"name\":\"Ravi Periyasamy\",\"id\":\"100001134571197\"},{\"name\":\"Meena Maya\",\"id\":\"100001167993223\"},{\"name\":\"Esakimuthu Ponnuswamy\",\"id\":\"100001204274362\"},{\"name\":\"Sarath Tammineni\",\"id\":\"100001210501201\"},{\"name\":\"Venkata Ramanan\",\"id\":\"100001226800542\"},{\"name\":\"Tushara Radhakrishnan\",\"id\":\"100001237922421\"},{\"name\":\"Muge Nadal\",\"id\":\"100001246279897\"},{\"name\":\"Abdul Rahman\",\"id\":\"100001260948544\"},{\"name\":\"Papia Dhar\",\"id\":\"100001280258011\"},{\"name\":\"Oishee Here\",\"id\":\"100001291977115\"},{\"name\":\"Soma Das\",\"id\":\"100001333795500\"},{\"name\":\"Bhuma Devi Anand\",\"id\":\"100001365288730\"},{\"name\":\"Rakesh Pradhan\",\"id\":\"100001400713213\"},{\"name\":\"Karthik Manickam\",\"id\":\"100001422774804\"},{\"name\":\"Sivaraman Chidambaram\",\"id\":\"100001426303144\"},{\"name\":\"Ramchandra Sistla\",\"id\":\"100001429719291\"},{\"name\":\"Priya Sundaram\",\"id\":\"100001442436061\"},{\"name\":\"Animesh Dey\",\"id\":\"100001482893324\"},{\"name\":\"Amit Sen\",\"id\":\"100001485460697\"},{\"name\":\"Asaithambi Dorairaj\",\"id\":\"100001496748743\"},{\"name\":\"VKrish Vidhya\",\"id\":\"100001499005023\"},{\"name\":\"Sudhakar Ks\",\"id\":\"100001504664886\"},{\"name\":\"Hemamalini Satish\",\"id\":\"100001534440678\"},{\"name\":\"Ganesh Narayanan\",\"id\":\"100001554547324\"},{\"name\":\"Rahul Suryabhan Chopde\",\"id\":\"100001570524005\"},{\"name\":\"Monica Shankar\",\"id\":\"100001643901867\"},{\"name\":\"Vijay Natesan\",\"id\":\"100001670901135\"},{\"name\":\"Jayanta Maitra\",\"id\":\"100001674963603\"},{\"name\":\"Venkat Kota\",\"id\":\"100001692423918\"},{\"name\":\"Sobers Manuel\",\"id\":\"100001696252473\"},{\"name\":\"Ganesh Khumaar Subbiah\",\"id\":\"100001701771780\"},{\"name\":\"Prasun Bhattacharya\",\"id\":\"100001712551876\"},{\"name\":\"Arun Prakkash\",\"id\":\"100001771078181\"},{\"name\":\"Raj Pandiyan\",\"id\":\"100001773240917\"},{\"name\":\"Vishnu Born To Rule\",\"id\":\"100001799869657\"},{\"name\":\"Gowtham An\",\"id\":\"100001805476713\"},{\"name\":\"Iqbal Ahmed\",\"id\":\"100001819670174\"},{\"name\":\"Vallimanalan Mariappan\",\"id\":\"100001829678560\"},{\"name\":\"Venkat Ram\",\"id\":\"100001832158365\"},{\"name\":\"Sabarisan Thangavelan\",\"id\":\"100001833709011\"},{\"name\":\"Sasi Kumar\",\"id\":\"100001849865614\"},{\"name\":\"Mark Lawrence\",\"id\":\"100001891427265\"},{\"name\":\"Subhojit Saha\",\"id\":\"100001910624069\"},{\"name\":\"Mohan Guru\",\"id\":\"100001913456344\"},{\"name\":\"Saran Raj\",\"id\":\"100001972561403\"},{\"name\":\"Paromita Chatterjee\",\"id\":\"100001995325186\"},{\"name\":\"Abani Mahata\",\"id\":\"100001996516336\"},{\"name\":\"Chandranath Das\",\"id\":\"100002029704723\"},{\"name\":\"Kaushik Mitra\",\"id\":\"100002040414349\"},{\"name\":\"Mariappan Padmanaban\",\"id\":\"100002047604614\"},{\"name\":\"Ashok Garrepelli\",\"id\":\"100002056257323\"},{\"name\":\"Swarnapriya Mariappan\",\"id\":\"100002090191641\"},{\"name\":\"Priyanka Nagaraj\",\"id\":\"100002100615260\"},{\"name\":\"Sayan Ghosh\",\"id\":\"100002178459870\"},{\"name\":\"Ramya Somasundaram\",\"id\":\"100002217355060\"},{\"name\":\"Kathiresan Manickavasagam\",\"id\":\"100002326707442\"},{\"name\":\"Mpw Dwayne\",\"id\":\"100002351868485\"},{\"name\":\"Tamojit Dasgupta\",\"id\":\"100002382197331\"},{\"name\":\"Karthikeyan Boopathy\",\"id\":\"100002386638909\"},{\"name\":\"Saravanan Markandan\",\"id\":\"100002406158966\"},{\"name\":\"V V Reddy Yarram\",\"id\":\"100002447316792\"},{\"name\":\"Pramit Dutta\",\"id\":\"100002458763203\"},{\"name\":\"Senthil Kumar\",\"id\":\"100002458810013\"},{\"name\":\"Balaji Srinivasan\",\"id\":\"100002472785158\"},{\"name\":\"Sakthi Vel\",\"id\":\"100002541994135\"},{\"name\":\"Julianne Roland\",\"id\":\"100002570476599\"},{\"name\":\"Shobana Rajagopalan\",\"id\":\"100002605050062\"},{\"name\":\"Subhashis Dey\",\"id\":\"100002614367994\"},{\"name\":\"Bala Sahu\",\"id\":\"100002614428193\"},{\"name\":\"Thanu Krishnan\",\"id\":\"100002619080352\"},{\"name\":\"Sathish Saravanan\",\"id\":\"100002643186417\"},{\"name\":\"Swamy Nathan\",\"id\":\"100002759018296\"},{\"name\":\"Yogaraj Rajakumar\",\"id\":\"100002759404255\"},{\"name\":\"Prabhakaranjayabalan Jayabalan\",\"id\":\"100002778341882\"},{\"name\":\"Kannathal Shanmugam\",\"id\":\"100002801164255\"},{\"name\":\"Mahesh Marupaka\",\"id\":\"100002856100620\"},{\"name\":\"Raj Thilak\",\"id\":\"100002976522174\"},{\"name\":\"Satheesh Kumar\",\"id\":\"100003048131597\"},{\"name\":\"Narayanan Subramanian\",\"id\":\"100003069717242\"},{\"name\":\"Manickam Karthik\",\"id\":\"100003124761307\"},{\"name\":\"Muthu Kumar\",\"id\":\"100003134764247\"},{\"name\":\"Sukanta Paul\",\"id\":\"100003222782628\"},{\"name\":\"Karthik Kannan\",\"id\":\"100003269841934\"},{\"name\":\"Shiny Wilson\",\"id\":\"100003319961301\"},{\"name\":\"Manikandan Mani\",\"id\":\"100003377147120\"},{\"name\":\"Arumugam Aasary\",\"id\":\"100003484015803\"},{\"name\":\"Sivaraman Ramasamy\",\"id\":\"100003565175799\"},{\"name\":\"Rajendra Tiwari\",\"id\":\"100003728651926\"},{\"name\":\"Rajan Maruthaiyan\",\"id\":\"100003842798702\"},{\"name\":\"Radha Krishnan\",\"id\":\"100004035619518\"},{\"name\":\"Ambiga Veni Sathish Kannan\",\"id\":\"100004209103458\"},{\"name\":\"A.m. Mariappan\",\"id\":\"100004298281310\"},{\"name\":\"Jalendra Madupu\",\"id\":\"100004347301061\"},{\"name\":\"Anto Rajesh\",\"id\":\"100004392241373\"},{\"name\":\"Arun ArunaChalam\",\"id\":\"100004759988185\"},{\"name\":\"Venkatesh Sai\",\"id\":\"100004927806542\"},{\"name\":\"Venkad Sai\",\"id\":\"100004928074254\"},{\"name\":\"Kedar Mahabaleshwarkar\",\"id\":\"100005849614240\"},{\"name\":\"Gracia Wald\",\"id\":\"100005990136537\"}],\"paging\":{\"next\":\"https://graph.facebook.com/1225410329/friends?access_token=CAAHDi8lqRfABANYuHXpgZAEDDoO9ZCdBv6RifZCTu3D0O0uHZAHrTF4oxhpewED4UZAwdZCRrz6hJhrRGKCWYpbxwYT1OZChMH16vCgrxK46nMYBxBZAGAakFtSfFO1iBldStIUDtmcfpl8OxW2fpf07&limit=5000&offset=5000&__after_id=100005990136537\"}}}"; 
	//String str = " {\"data\":{\"app_id\":496480123766256,\"is_valid\":true,\"application\":\"Social Event Planner\",\"user_id\":1225410329,\"issued_at\":1372198089,\"expires_at\":1377382089,\"scopes\":[\"email\",\"read_friendlists\"]}}";
	JSONObject jobj = new JSONObject(str);
	/*Gson gsonObj = new Gson();
	Type listType = new TypeToken<List<String>>() {}.getType();
	List<String> respList = gsonObj.fromJson("{name:\"Jesus\"}", listType);*/
	/*System.out.println("Converted JSON 1 : " + jobj.get("friends"));
	System.out.println("Converted JSON 2 : " + jobj.names());
	
	JSONObject j2 = (JSONObject) jobj.get("friends");
	JSONArray j3 = (JSONArray) j2.get("data");
	System.out.println("Converted JSON 3 : " + j2.get("data"));
	System.out.println("Converted JSON 3 : " + j3.length());*/
	String jsonStr = (String) session.getAttribute("FBFRNDJSON");
	JSONArray j3 = new JSONArray(jsonStr);
	JSONObject jo = null;
	int j = 0;
	for(int i = 0; i < j3.length() ; i++ ) {
		//JSONObject jo = j3.getJSONObject(i);
		//System.out.println(i + " - ID " + jo.get("id") + " - " + jo.get("name"));
 %>
 
 
<div class="row-fluid">
    <div class="span4">
    	
      <% jo = j3.getJSONObject(i); %>
      <input type="checkbox" value="<%= jo.get("id") + "-" + jo.get("name") %>" name=<%= "FB" + jo.get("id") %>>  <%= jo.get("name") %>
    </div>
    <%
    j = ++i;
    if(j < j3.length()) {
    	jo = j3.getJSONObject(j); %>
    <div class="span4" >
    
      <input type="checkbox" value="<%= jo.get("id") + "-" + jo.get("name") %>" name=<%= "FB" + jo.get("id") %>>  <%= jo.get("name") %>
    </div>
    <% } %>
    <%
    j = ++i;
    if(j < j3.length()) {
    	jo = j3.getJSONObject(j); %>
    <div class="span4" >
    
      <input type="checkbox" value="<%= jo.get("id") + "-" + jo.get("name") %>" name=<%= "FB" + jo.get("id") %>>  <%= jo.get("name") %>
    </div>
    <% } 
    }
    %>
   
  <div class="control-group">
    <div class="controls" align=center>
      <button type="button" class="btn btn-primary" onclick="continueNoRedirect()">Continue</button>
      <%
      	if(!disableBut.equals("True"))
      	{
      %>
      <button type="button" class="btn btn-primary" onclick="continueRedirect()">Continue & Add G+ Friends</button>
       <%
      	}
      %>
    </div>
  </div>
  </div>
</form>				
<!-- form class="form-horizontal" action="/InvitationHandler" method="post">
 <input type="hidden" name="HiddenControl" value="Step2">
 <input type="hidden" name="SocialType" value="GPlus">
 <div class="control-group">
  	
    <div class="controls" align=left>
      If you want to invite Google+ friends for this Event, please click "G+ Friends" button. 
    </div>
  </div>
  <div class="control-group">
  	
   
  <div class="control-group">
    <div class="controls" align=center>
      <button type="submit" class="btn btn-primary">G+ Friends</button>
    </div>
  </div>
  </div>
</form-->	

      

      <hr>

      <div class="footer">
        <p>&copy; Summer 2013 - Social Computing Science</p>
      </div>

    </div> <!-- /container -->

    <!-- Le javascript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    
    
    

  </body>
</html>
