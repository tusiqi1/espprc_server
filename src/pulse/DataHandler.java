/**

* This class stores all the data and reads the input data file 

 * Ref.: Lozano, L., Duque, D., and Medaglia, A. L. (2013). 

 * An exact algorithm for the elementary shortest path problem with resource constraints. Technical report COPA 2013-2  

 * @author L. Lozano & D. Duque

 * @affiliation Universidad de los Andes - Centro para la Optimizacin y Probabilidad Aplicada (COPA)

 * @url http://copa.uniandes.edu.co/

 **/

package pulse;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.StringTokenizer;


public class DataHandler {
	
	// Graph attributes
	public static int numArcs;			//Number of arcs
	public static int n;				//Number of nodes
	static int lastNode;				//Last node
	public static int Q;				//Capacity limit

	public static int[][] arcs;			// All the arcs of the network stored in a vector where arcs[i][0] = tail of arc i and arcs[i][1] = head of arc i 
	public static double[] distList;	// Distance attribute for any arc i 
	public static double[] timeList;	// Time attribute for any arc i
	public static double[] costList;	// Cost attribute for any arc i
	public static double[] loadList;	// Load attribute for any arc i
	public static double[][] distance;	// Distance matrix
	public static double[][] cost;		// Cost matrix
	
	public static int[] demand;			// Demand for each node
	public static int[] service;		// Service duration for each node
	public static int[] tw_a;			// Lower time window for each node
	public static int[] tw_b;			// Upper time window for each node
	
	public static double[] x;			// x coordinate for each customer 
	public static double[] y;			// y coordinate for each customer
		
	private GraphManager G;				// Data structure that holds the graph
	
	// Input file information
	private String  instanceType;
	private int instanceNumber;
	public static String CvsInput;
	
	// Other stuff
	public static int numThreads;		// Number of threads
	public static Thread[] threads;		// Threads
	public static int boundStep;		// Step size for the bounding procedure
	public static double[] pi;			// dual variables
	public static Random r;				// Random numbers generator 
	

	public DataHandler(String dataFile, String type , int number, int nThreads, int step) {
		CvsInput = dataFile;
		instanceType = type;
		instanceNumber=number;
		numThreads = nThreads;
		boundStep = step;

		threads = new Thread[DataHandler.numThreads+1];
		for (int i = 0; i < threads.length; i++) {
			threads[i] = new Thread();
		}
	}
	
	
	
	// Generates random dual variables to build a random subproblem
	public void generateRandomInstance(int seed) {
		r = new Random(seed);		// Initialize random generator
		pi = new double[n+2];		// Initialize dual variables vector

		pi[0] = 0;

		for (int i = 1; i <= n; i++) {
			pi[i] = r.nextInt(11);		// Generate random dual variables between 0 and 10
		}
		
		for (int i = 0; i < numArcs; i++) {
			costList[i] = distList[i]-pi[arcs[i][0]]; //Calculate reduced cost with the dual variable of the tail node of each arc
		}

		for (int i = 0; i <= n; i++) {
			for (int j = 0; j <= n; j++) {
				
				cost[i][j]= distance[i][j]-pi[i]; //Calculate reduced cost with the dual variable of the tail node of each arc
			}
		}
	
	}
	
	public void generateInstance(int instance) {
	// Dual variables vectors taken from the CG procedure	
	 if(instance==201){
		double[] PI = {0.0,3.8237089201877836,5.935211267605691,3.5788732394366782,7.671830985915506,6.599999999999994,4.678873239436655,11.300000000000072,16.204225352112584,5.099999999999973,12.976291079812267,6.776291079812243,12.121126760563307,6.208450704225527,12.900000000000034,18.743661971831017,0.6999999999999886,18.164788732394427,12.098356807511898,10.623708920187784,14.301291079812213,15.335211267605604,8.099999999999994,8.43521126760563,8.878873239436668,23.042253521126618,14.214084507042223,0.5999999999999943,0.7999999999999545,26.52887323943669,18.499999999999915,4.376291079812155,14.477582159624472,7.247417840375427,17.850000000000094,29.799999999999997,22.476291079812142,4.199999999999996,20.352112676056265,17.021126760563277,6.28591549295777,8.099999999999994,12.75633802816887,31.16478873239444,5.300000000000068,13.80000000000009,14.600000000000009,1.5237089201877154,16.66478873239444,39.23521126760568,14.007042253521107,15.048122065727835,10.67629107981211,8.70000000000001,15.235915492957815,6.135915492957793,11.999999999999936,6.1563380281688715,4.756338028168925,4.3352112676056045,5.400000000000006,12.200000000000017,6.9525821596244235,26.847417840375446,19.40000000000003,37.94553990610343,15.521126760563313,30.90000000000004,7.721126760563319,10.123708920187838,7.798708920187778,20.40704225352111,6.578873239436646,9.799999999999972,16.33591549295781,5.300000000000011,11.928873239436705,5.300000000000026,5.599999999999959,6.77887323943666,3.70000000000001,2.7230046948355415,9.499999999999911,11.20000000000001,12.956338028168986,2.752112676056356,20.487323943661863,16.48732394366207,6.399999999999917,2.3999999999999773,21.001291079812308,6.299999999999997,1.700000000000017,11.700000000000017,9.142253521126836,1.0000000000000036,8.435211267605553,4.900000000000006,11.26478873239446,6.587323943661858,3.299999999999983};
		this.pi = PI;
	 }
	 else if(instance==202){
		double[] PI ={0.0,2.200000000000003,5.599999999999952,1.1000000000000085,8.423809523809622,1.8000000000000114,9.380555555555656,3.5,10.158333333332985,6.0999999999999375,13.735317460317347,6.599999999999966,0.29999999999996163,6.400000000000006,10.251984126984016,16.325000000000088,1.2527777777780447,11.099999999999994,20.30000000000009,11.000000000000043,15.299999999999983,1.200000000000017,2.0726190476186197,35.595634920635035,9.463888888889038,21.800793650793302,0.16865079365103952,0.09999999999996587,3.731349206349016,25.20000000000003,17.400000000000034,3.5,15.299999999999997,7.595238095237917,8.295238095237977,29.904761904761997,13.052777777777813,1.600000000000028,23.500000000000085,11.709126984126762,10.447619047618819,15.636904761905036,11.584126984126765,28.427777777777656,3.7027777777785484,22.113888888888617,20.600000000000023,14.816666666666872,3.3305555555553386,37.372222222222206,3.304761904762046,7.8333333333336554,3.799999999999997,8.8,15.828571428571502,7.363888888889036,11.51428571428557,5.372222222222355,4.9523809523811835,2.60000000000007,8.730555555555316,17.116666666666205,5.336111111111094,25.50000000000002,20.361111111111263,47.14087301587317,12.59523809523806,13.200000000000003,7.499999999999993,11.636111111111113,13.09523809523806,8.100000000000001,0.5999999999999943,11.461904761904886,16.928571428571495,0.5857142857144382,15.000000000000014,4.300000000000033,16.64007936507921,4.399999999999977,2.500000000000121,3.490476190476002,3.8999999999999773,0.7000000000000028,16.01666666666718,1.8999999999999773,19.80555555555539,13.227777777777698,10.125000000000128,1.300000000000078,16.833333333333478,3.0527777777777754,2.8000000000000003,4.846825396825898,0.30000000000000426,1.2999999999999727,0.7999999999999687,7.178174603174295,1.6999999999999957,7.727777777777462,11.753174603174118};
		this.pi = PI;
	 }
	 else if(instance==203){
		 double[] PI ={0.0,2.0999999999999197,7.900000000000002,0.29999999999999716,11.960000000000218,3.5999999999999197,11.823333333333423,5.926666666666787,17.34999999999968,20.993333333333023,15.416666666666675,11.609999999999719,4.199999999999989,9.760000000000046,3.599999999999996,10.180000000000518,0.7000000000000153,15.99999999999993,6.526666666666722,3.4633333333334733,3.200000000000017,4.500000000000039,4.200000000000056,28.78000000000003,12.600000000000058,17.92666666666686,3.09999999999998,0.9999999999999929,0.49999999999999645,3.826666666666682,17.02666666666677,1.7000000000000242,7.8033333333330255,7.659999999999783,6.100000000000001,19.2466666666667,11.949999999999827,1.0999999999999392,28.823333333333082,14.606666666666833,12.899999999999967,11.879999999999967,7.546666666666974,13.219999999999564,5.44666666666695,19.079999999999654,9.580000000000226,3.6366666666666427,6.700000000000025,32.72666666666679,0.0,5.699999999999974,5.599999999999987,7.400000000000038,7.000000000000003,4.573333333332945,10.219999999999997,5.999999999999974,3.899999999999998,1.9266666666668195,4.199999999999989,2.4733333333332226,8.899999999999991,6.33333333333345,19.400000000000105,51.21333333333345,24.580000000000215,16.43333333333314,10.199999999999793,8.900000000000006,12.85666666666648,0.0,0.8999999999999657,2.199999999999992,9.460000000000035,1.4399999999999453,17.80000000000007,8.79999999999999,11.626666666666898,3.826666666666739,3.6999999999999496,2.299999999999997,3.8999999999999755,1.9000000000000048,6.570000000000712,1.7733333333331691,10.936666666666525,13.383333333333262,13.026666666666861,3.500000000000047,2.0000000000000284,1.0099999999997937,12.119999999999656,14.26666666666683,0.29999999999997307,0.9333333333333549,0.0,1.9999999999999951,1.3733333333331572,9.853333333333444,7.693333333333515};
		this.pi = PI;
	 }else if(instance==204){
		 double[] PI ={0.0,5.649628080402899,6.0622582049438325,1.000000000000008,10.873766892531378,2.7142417761289925,2.199999999999995,7.699999999999997,7.898888973010013,10.445396903508943,6.8496280804028835,9.487288488472782,4.299999999999981,7.382085399553241,6.627482303062328,8.451802816368334,3.741543324374454,8.460575576333559,7.7546030964909205,9.890049967823744,5.799999999999997,3.4597247984254973,1.9999999999999627,13.201151720482734,9.876184843093743,13.435604913502441,5.799999999999983,0.6999999999999211,3.199999999999978,8.523815156906377,7.250371919597109,6.314241776128995,9.63383711246567,2.7139010864218385,11.799999999999985,7.990049967823788,7.264273006019012,1.2000000000000028,26.877873149865785,9.217477381989116,2.6000000000000005,8.581889502971649,6.498998750804345,10.399999999999912,8.105084793882357,7.245396903509041,13.700000000000035,5.200000000000083,7.204852935609697,13.668698186774117,5.3999999999999,7.67893591248084,5.000000000000018,8.8,7.0,7.700000000000044,2.9000000000000323,6.200000000000015,3.900000000000024,1.0637411515311523,4.7994747700342915,3.1000000000000005,9.894159064239208,8.595890903584666,15.696756823257147,18.720058106522444,12.799999999999924,35.15450467502016,3.125040693492607,4.385074951735751,6.150371919597182,5.42504069349257,0.6000000000000032,4.467141424082337,11.408738690994563,2.1818895029716376,17.55996233486004,7.5,3.0238511185980688,11.741481810955024,3.6749593065074,4.631014119695475,5.0893061286294685,6.814767006094633,15.481098345762021,1.800000000000011,8.616913351251114,14.69087614036419,5.0304112881857375,2.9000000000000083,2.010815951848913,1.7584566756255517,7.4754315402959515,0.966541431654103,2.900000000000004,1.2274823030623363,2.7637411515311547,1.9999999999999574,1.6999999999999886,12.286315630086904,15.701001249195647};
		this.pi = PI;
	 }else if(instance==205){
		 double[] PI ={0.0,4.599999999999994,6.6107556618580645,6.507937812990541,5.033395620001583,12.031500859556067,5.393557067045491,11.484098213618402,7.100000000000023,7.067975932431393,5.599999999999994,4.599999999999968,6.799999999999983,4.099999999999947,10.735395022049524,14.400000000000006,1.3999999999999897,17.599999999999994,9.777191867852551,7.381822258763812,5.540791538978972,2.8646049779504636,5.763771582330683,1.5000000000000178,11.235395022049524,9.564604977950488,11.299999999999983,2.0558225577396882,5.700000000000017,20.81116301666787,13.400000000000013,5.370509754092353,13.71841692204201,11.052612302862656,11.419519396068452,14.5,14.040896180581768,3.4000000000000363,18.956734434561607,15.30615516854769,14.776186561028444,8.099999999999994,10.648471485163338,24.419624037670967,8.700000000000019,13.494622169070894,18.38678899768276,13.882678077583972,26.37821959787736,18.49460348307046,12.600000000000023,9.491232528589592,9.983866507212749,3.1099222662382644,6.335395022049545,10.499999999999996,12.883922565213926,7.263177367516152,3.9000000000000465,2.599999999999966,6.325229837805605,3.2000000000000015,6.162183272292367,13.874512295388278,19.667478884819456,33.23208386277004,17.700000000000017,32.668790642051135,1.0826818147844754,8.279254802302148,11.168248748038039,11.176683608640403,7.711163016667868,4.399405785185504,8.733395620001625,5.39999999999997,8.11116301666788,9.900000000000006,4.856667164959989,3.656667164959971,7.317318185215502,6.597604454742546,6.564604977950532,5.584632633231141,11.890126317363125,5.052784214066731,15.199999999999994,8.900000000000006,6.780701098736721,2.6000000000000085,9.24079153897899,6.300000000000023,2.194622169070948,8.407324912175962,7.7253606398087316,7.116133492787222,4.004140817699197,6.211465729875209,3.694622169070905,5.8692615292621175,4.107324912175948};
		this.pi = PI;
	 }else if(instance==206){
		 double[] PI ={0.0,2.1999999999999886,5.561056268509461,3.8000000000000043,3.8778874629812847,2.3999999999999773,8.256161566304737,5.519471865745327,3.2079220138202906,7.231766205988734,11.962763244488237,4.599999999999994,5.677336294833822,6.812323132609503,10.044225074037557,15.83894373149058,4.6000000000000085,17.599999999999984,10.180528134254718,8.176900296150015,5.97695788088182,2.973379401118887,7.3084155972359905,2.676620598881163,11.493122737742567,5.1778874629812375,3.0999999999999943,0.09999999999999609,3.1999999999999886,19.38000575847336,14.351982560052498,3.0000000000000284,5.575686903586705,7.361607436656648,6.733033070088668,23.86581523527493,17.412870187561918,1.1778874629812517,22.177887462981253,20.180528134254708,13.190210595590685,7.505281342546867,10.27788746298122,24.38366238894363,8.07662059888117,24.94329137874294,27.035143139190527,9.958695294504608,4.0615498519249655,20.142571569595145,6.277336294833807,11.32674399473521,4.299999999999983,2.9766205988811754,10.606877262257399,12.862759131293094,10.525246791707708,5.976127015465621,3.9000000000000057,1.24070417900628,7.388450148075019,4.016831194471832,10.327940934517919,12.491530931227363,19.527940934517908,42.310661401777004,12.677509049029304,34.10968246133598,1.7858012504113177,5.900000000000011,14.501287430075749,3.977336294833796,0.5999999999999943,6.7115498519248895,10.606548206646991,0.6999999999999886,8.49037512339585,7.414198749588664,5.600000000000115,6.50000000000005,4.806877262257444,4.766966929911078,3.9000000000000252,1.6194718657452931,5.992077986179698,1.722112537018754,13.367871832839807,8.400000000000006,9.572778874629767,3.9379565646595998,9.437236755511769,3.3557749259624483,9.134435669628203,6.977887462981263,0.7999999999999972,0.30000000000001137,1.6592958209937256,5.6185916419875355,2.161056268509355,9.321952122408572,6.840704179006274};
		this.pi = PI;
	 }else if(instance==207){
		 double[] PI ={0.0,2.2000000000000117,5.644458631256217,3.7741317671092514,9.522267620020688,3.4447650663941047,8.981613891726166,7.700000000000072,14.776378958120313,10.400000000000004,10.785316649642768,5.588815117466771,4.300000000000052,6.500000000000058,5.899999999999993,9.455541368743866,3.700000000000019,16.799999999999955,5.396169560776308,6.0,6.374131767109306,4.844458631256128,7.44445863125625,3.9222676200207367,11.199999999999925,9.074131767109273,3.100000000000014,0.09999999999995168,0.5000000000000764,8.00000000000006,8.588815117466657,2.8259193054136027,5.2888151174665,11.225919305413887,7.174131767109202,15.377298263533618,20.4923646578141,1.1444586312562228,32.342339121552364,24.77962206332938,7.88886618999004,8.099999999999994,12.622216547497473,7.766675178753598,9.58544433095013,17.956332992849475,6.12883043922408,4.300000000000036,5.114734422880487,23.11762002042879,4.062946884575901,7.000000000000297,4.474080694586408,5.300000000000011,7.6999999999999895,7.929519918283878,5.1036006128700775,5.3999999999999915,3.89999999999998,1.3111082737487703,4.070505617977421,2.500000000000014,11.6147344228805,9.400000000000071,22.903549540347285,36.66294688457594,23.256562819203936,32.62032686414745,4.274131767109219,6.63705311542417,14.269228804902335,0.10000000000002629,2.255541368743926,1.7999999999999474,6.629673135852959,2.700000000000003,9.16294688457598,8.900000000000036,15.422701736466257,5.599999999999994,4.5,3.3741317671091995,3.8999999999999773,0.5999999999999943,6.162512768130967,1.8000000000000114,7.200000000000047,11.194458631256206,11.132303370786905,2.9000000000000057,3.3000000000000487,2.6888917262512226,8.111108273748806,9.011108273748698,0.9999999999999964,0.29999999999999716,1.5888917262512177,1.9999999999999716,1.577757916241337,6.35171092952014,5.2888917262512605};
		this.pi = PI;
	 }else if(instance==208){
		 double[] PI ={0.0,7.873290452813247,7.6637986885011005,1.3976003631109923,9.299999999999983,2.3000000000000362,3.3000000000000353,7.660385208012217,9.7423916341173,14.017078391322165,8.488159123756203,7.2732904528132245,4.710494619032254,8.100000000000039,6.700000000000187,9.897600363110902,3.6000000000000085,6.800000000000278,8.133061716893035,9.820645716129068,5.800000000000011,5.486199161501812,2.9499999999999638,27.69313278627715,8.391307676688943,5.700000000000028,5.8000000000000105,2.199999999999979,4.580751902151196,11.021544534823278,6.500000000000032,6.081103904635785,7.299575136465071,4.5745723295233205,8.555836647913996,8.400000000000084,9.01216361486578,1.2992600422835858,18.999999999999915,7.4362013114990795,2.844625482255857,4.949999999999935,8.0,10.899999999999896,6.109343892212509,7.200000000000088,13.784706465522136,5.263798688501179,7.528459167951584,8.851035582468416,4.461308273910172,7.9480421877427645,7.062318773067731,8.8,8.799999999999827,7.699999999999967,3.6976003631106655,6.0,3.8999999999999773,1.0992600422836651,4.263798688501039,5.06305873078503,7.905368784415011,7.999732205777731,20.699999999999932,16.60367554138114,12.800000000000006,15.713798688500983,1.4847064655221862,4.699477550435717,4.275214701210122,5.4999999999999485,1.7999999999999448,1.7999999999999448,10.999999999999966,2.20000000000021,9.79676556658441,8.844377396352362,6.892668506109393,6.311750098541899,5.2000416860766,3.373932824500077,4.573290452813238,6.936201311499105,6.576377014130155,1.1999999999997482,9.763058730785033,12.48380167460977,5.551957812257239,4.9623187730677065,4.603116183515566,3.763058730784744,7.2352815900429,2.7999999999998657,2.098520084566864,1.300000000000174,1.9992600422834463,1.599999999999841,1.8999999999999186,8.0,12.787756118535725};
		this.pi = PI;
	 }else if(instance==209){
		 double[] PI ={0.0,6.6652892561980694,15.020385674931308,11.207196969697026,11.299999999999997,5.533539944903541,2.8590220385675007,9.431129476584022,6.203030303030117,7.12871900826476,7.782920110192853,4.51707988980713,6.799999999999998,2.3999999999999213,11.744696969696715,10.851928374656026,4.792561983471167,12.351446280991551,15.78574380165282,8.200000000000045,11.924276859503964,6.720385674931272,4.900000000000149,18.550757575757256,15.534710743801806,9.434710743801855,6.665289256198097,2.2000000000000144,5.55151515151562,30.348484848484475,13.574207988981012,5.8844352617080125,13.700000000000115,2.6000000000000227,14.300482093664158,8.141356749311068,4.300000000000042,4.682920110192656,18.05161845730009,4.100000000000023,3.8347107438016295,5.711639118457335,0.8334710743800979,21.514325068870317,5.486501377410337,7.200000000000061,12.900378787879168,14.61707988980712,25.20664600550885,22.576101928374776,8.8,6.803960055096123,10.168870523415968,3.2861570247932272,6.299999999999969,7.699999999999932,4.350000000000083,5.999999999999946,5.600000000000079,2.8761019283745526,6.379889807162451,5.699931129476967,7.000000000000089,12.068870523415766,20.01404958677689,17.13267906336087,12.699999999999989,17.49999999999996,5.318836088154694,8.584435261707974,3.2413567493111266,16.30000000000001,3.676928374655832,4.950757575757301,7.349999999999909,6.509917355372085,6.400000000000034,1.881163911845352,1.1116391184576564,0.08116391184533223,2.992803030302921,9.132679063360872,6.300000000000042,8.880509641873534,6.999999999999877,7.133539944903613,11.577444903581581,9.348071625343977,3.6999999999999886,2.2201101928378275,12.103202479339014,10.700000000000095,1.7000000000000308,0.776101928374767,10.779545454545866,9.575688705234265,6.023898071625162,6.5761019283747855,4.599999999999902,3.4999999999999862,1.099999999999966};
		this.pi = PI;
	 }else if(instance==210){
		 double[] PI ={0.0,3.900000000000034,4.578333333333497,3.312991452991434,8.257051282051197,2.400000000000011,6.010512820512664,7.200000000000109,10.747735042734996,13.587008547008477,16.42735042735039,7.845598290598333,6.799999999999997,7.867692307692072,3.5999999999999943,15.799999999999983,4.78948717948732,15.599999999999985,7.789487179487171,7.735085470085416,6.327350427350385,3.9548290598294145,1.1882478632479305,11.564615384615614,11.199999999999989,17.75705128205113,2.3664529914530057,0.2000000000000366,2.8129914529915325,13.999999999999986,12.212991452991528,5.300000000000026,11.800000000000011,13.25824786324769,6.10000000000003,28.54529914529906,17.600000000000048,1.2000000000000126,26.23585470085466,3.3340170940168647,8.332179487179282,11.323504273504145,14.375299145299191,26.246025641025817,8.596794871794785,22.38594017094033,13.700000000000049,6.035085470085428,6.600000000000023,20.914059829059855,4.5,5.400000000000098,3.0999999999999943,8.746666666666567,6.988247863247896,7.542948717948793,7.057179487179354,4.400000000000006,3.9000000000000057,1.946025641025626,6.499999999999977,4.81051282051267,10.394743589743621,8.027350427350374,25.19999999999999,43.04175213675217,11.627350427350382,41.5684615384613,6.587008547008502,2.3999999999999826,9.887008547008639,2.7000000000000455,2.5000000000000178,6.79739316239333,10.200000000000024,2.4117521367520816,18.74170940170962,5.311752136752059,5.60000000000003,6.858290598290456,8.242948717948831,2.299999999999983,5.180683760683705,2.714059829059846,10.303547008546973,1.989487179487309,8.256196581196786,11.399999999999993,8.002478632478802,3.6999999999999966,3.317948717948582,3.2000000000000077,7.478675213675277,3.2999999999999883,3.489487179487341,0.3460256410256086,1.853974358974348,7.753974358974357,2.499999999999977,9.110512820512723,15.053974358974152};
		this.pi = PI;
	 }else if(instance==211){
		 double[] PI ={0.0,7.318687734268403,9.279017472566501,4.128267149569765,11.199999999999866,3.310119959204781,3.29999999999999,7.600000000000034,5.099999999999964,6.00181334021917,11.033322576513662,6.83795389050168,7.212177551057785,6.860993814141278,5.899999999999995,8.604846039847853,4.599999999999994,13.186391682630289,6.496164472957142,11.237953890501792,7.018347342651651,8.267114245037714,2.0037605169118065,15.480466001917529,11.200000000000104,7.036284305625479,7.722448251179969,2.2000000000000366,4.797560397252243,16.838407106972028,10.104356451374546,6.2623013018689555,14.888766510811404,6.468520358447869,8.233292981771289,14.380888621483177,8.169272414818792,3.3976336212266314,18.6,4.196239483088216,5.691201744583804,10.996239483088162,8.32746781564733,15.246246983115912,7.402832279911891,7.699999999999989,14.04144991126434,5.437953890501649,11.541045828878863,12.96405016169492,7.903760516911709,9.534627653140955,12.000000000000005,0.4000000000000057,6.999999999999872,5.863715694374687,6.600000000000154,5.169789239013383,3.899999999999935,2.399999999999922,7.505935425048847,5.972478109834894,5.700000000000047,9.399999999999977,18.175907781003474,16.022450102083116,12.700000000000038,21.089526954657646,4.509342491272802,9.39999999999995,3.8644190994439604,8.156301233153368,2.903760516911725,2.903760516911758,11.37909112443009,3.233767560336735,9.06277606200554,3.404356451374553,4.968520358447869,3.6043564513745405,5.390657508727363,4.8346276531409655,5.766136828911765,5.869972355954515,3.310119959204781,0.7703195248324639,9.835782658834628,6.258906020900219,4.337698698131034,5.160993814141307,2.235580900555934,8.002366378773262,1.5000000000000093,3.7234617958071596,2.900000000000042,6.251193288420319,3.5388511547869532,4.115728865259968,6.338487212364744,3.8873409622156627,2.8873409622157613};
		this.pi = PI;
	 }	
	 	 
		for (int i = 0; i < numArcs; i++) {
			costList[i] = distList[i]-pi[arcs[i][0]]; //Calculate reduced cost with the dual variable of the tail node of each arc
		}

		for (int i = 0; i <= n; i++) {
			for (int j = 0; j <= n; j++) {
				
				cost[i][j]= distance[i][j]-pi[i]; //Calculate reduced cost with the dual variable of the tail node of each arc
			}
		}
	
	}

	public void generateInstance(double[] dual){
		this.pi = dual;
		for (int i = 0; i < numArcs; i++) {
			costList[i] = distList[i]-pi[arcs[i][0]]; //Calculate reduced cost with the dual variable of the tail node of each arc
		}

		for (int i = 0; i <= n; i++) {
			for (int j = 0; j <= n; j++) {

				cost[i][j]= distance[i][j]-pi[i]; //Calculate reduced cost with the dual variable of the tail node of each arc
			}
		}
	}
	/**
	 * Read a Solomon instance
	 * @throws NumberFormatException
	 * @throws IOException
	 */
	public void readSolomon(int numNodes) throws NumberFormatException, IOException {
		
		readCapacity();
		
		File file = new File(CvsInput);
		BufferedReader bufRdr = new BufferedReader(new FileReader(file));
		String line = bufRdr.readLine(); //READ Num Nodes
		
		
		n = numNodes; 
		StringTokenizer t = new StringTokenizer(line, " ");
		
		x = new double[n+1]; 
		y = new double[n+1]; 
		demand = new int[n+1]; 
		service =  new int[n+1];
		tw_a =  new int[n+1];
		tw_b =  new int[n+1];
		
		String[] stringReader = new String[7];
		int indexString = 0;
		while (t.hasMoreTokens()) {
			stringReader[indexString] = t.nextToken();
			indexString++;
		}
		
		
		x[0] =Double.parseDouble(stringReader[1]);
		y[0] =Double.parseDouble(stringReader[2]);
		service[0] = (int)(Double.parseDouble(stringReader[6]));
		demand[0]=(int)(Double.parseDouble(stringReader[3]));
		tw_a[0]= (int)(Double.parseDouble(stringReader[4]));
		tw_b[0]= (int)(Double.parseDouble(stringReader[5]));
		G = new GraphManager(n+1); 
		int auxNumArcs = (n+1)*(n+1)-(n+1);
		G.addVertex(new Node(0,demand[0],service[0],-tw_b[0],tw_b[0]));
		int customerNumber = 1;
	
		while (customerNumber<=n) {
			indexString=0;
			stringReader= new String[7];
			line = bufRdr.readLine();
			t = new StringTokenizer(line, " ");
			while (t.hasMoreTokens()) {
				stringReader[indexString] = t.nextToken();
				indexString++;
			}
			x[customerNumber] =Double.parseDouble(stringReader[1]);
			y[customerNumber] =Double.parseDouble(stringReader[2]);
			service[customerNumber] = (int)(Double.parseDouble(stringReader[6]));
			demand[customerNumber]=(int)(Double.parseDouble(stringReader[3]));
			tw_a[customerNumber]= (int)(Double.parseDouble(stringReader[4]));
			tw_b[customerNumber]= (int)(Double.parseDouble(stringReader[5]));
			
			G.addVertex(new Node(customerNumber,demand[customerNumber],service[customerNumber], tw_a[customerNumber],tw_b[customerNumber]));
			customerNumber++;
		}
		
		
		distance = new double[n + 1][n + 1];
		cost = new double[n + 1][n + 1];
		distList = new double[auxNumArcs];
		costList = new double[auxNumArcs];
		loadList = new double[auxNumArcs];
		timeList = new double[auxNumArcs];
		arcs = new int[auxNumArcs][2];
		int arc = 0;
		for (int i = 0; i <= n; i++) {
			for (int j = 0; j <= n; j++) {
				// Redondio a un decimal pero est� x10 para que quede entero
				// para el SP
				double d_ij = Math.sqrt(Math.pow((x[i] - x[j]), 2)	+ Math.pow((y[i] - y[j]), 2));
				double dINT = Math.floor(d_ij*10)/10;
				distance[i][j] = dINT;
				distance[j][i] = dINT;

				
				cost[i][j] = dINT ;
				cost[j][i] = dINT;
				// PODAR CON TW
				if ((i==0 && (i!=j))  ||((i!=j) && tw_a[i] + service[i] + dINT <= tw_b[j]) ) {
					distList[arc] = dINT;
					costList[arc] = cost[i][j];
					arcs[arc][0] = i;
					arcs[arc][1] = j;
					timeList[arc] = dINT + service[i];
					loadList[arc] = demand[j];
					int a1 = arc;
					G.nodes[i].magicIndex.add(a1);
					
					arc++;
				}
			}
		}
		
		numArcs =arc;
		
		for (int i = 0; i < n; i++) {
			G.getNodes()[i].autoSort();
		}
	
	}

private void readCapacity() throws IOException {
		File file = new File("Solomon Instances/capacities.txt");
		BufferedReader bufRdr = new BufferedReader(new FileReader(file));
		for (int i = 0; i < 6; i++) {
			String line = bufRdr.readLine(); //READ Num Nodes
			String[] spread = line.split(":");
			if(instanceType.equals(spread[0])){
				int serie = Integer.parseInt(spread[1]);
				if (instanceNumber-serie<50) {
					Q=Integer.parseInt(spread[2]);
					return;
				}
			}
		}
		
		
	}

}