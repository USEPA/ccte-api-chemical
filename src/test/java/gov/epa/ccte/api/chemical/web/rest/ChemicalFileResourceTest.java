package gov.epa.ccte.api.chemical.web.rest;

import org.junit.jupiter.api.BeforeEach;

//This will test REST end-points in the ChemicalFileResource.java using WebMvcTest and MockitoBean

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.http.MediaType.TEXT_PLAIN;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import gov.epa.ccte.api.chemical.domain.ChemicalDetail;
import gov.epa.ccte.api.chemical.repository.ChemicalDetailRepository;
import gov.epa.ccte.api.chemical.domain.ImageFormat;
import gov.epa.ccte.api.chemical.service.ChemicalImageUtils;

import java.util.*;

@ActiveProfiles("test")
@WebMvcTest(ChemicalFileResource.class)
@RunWith(MockitoJUnitRunner.class)
public class ChemicalFileResourceTest {
	
	@Autowired
    private MockMvc mockMvc;
	
	@MockitoBean
	private ChemicalDetailRepository detailRepository;
	@MockitoBean
	private ChemicalImageUtils chemicalImageUtils;
	
	private ChemicalDetail fileDetails;
	
	@BeforeEach
	public void setUp() {
		
		fileDetails = ChemicalDetail.builder()
				.dtxsid("DTXSID7020182")
				.dtxcid("DTXCID30182")
				.genericSubstanceId(20182)
				.smiles("CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1")
				.molFile("\r\n"
				+ "  Mrv1805 02272414302D          \r\n"
				+ "\r\n"
				+ "  0  0  0     0  0            999 V3000\r\n"
				+ "M  V30 BEGIN CTAB\r\n"
				+ "M  V30 COUNTS 17 18 0 0 0\r\n"
				+ "M  V30 BEGIN ATOM\r\n"
				+ "M  V30 1 C 0.0433 1.4565 0 0\r\n"
				+ "M  V30 2 C 1.1321 0.3676 0 0\r\n"
				+ "M  V30 3 C 2.6098 0.7047 0 0\r\n"
				+ "M  V30 4 C 3.6727 -0.4101 0 0\r\n"
				+ "M  V30 5 C 3.232 -1.9051 0 0\r\n"
				+ "M  V30 6 C 1.7457 -2.2595 0 0\r\n"
				+ "M  V30 7 C 0.6741 -1.1361 0 0\r\n"
				+ "M  V30 8 O 4.3209 -2.9941 0 0\r\n"
				+ "M  V30 9 C -1.0457 0.3676 0 0\r\n"
				+ "M  V30 10 C -0.6481 -1.1274 0 0\r\n"
				+ "M  V30 11 C -1.737 -2.2162 0 0\r\n"
				+ "M  V30 12 C -3.232 -1.8188 0 0\r\n"
				+ "M  V30 13 C -3.6295 -0.3324 0 0\r\n"
				+ "M  V30 14 C -2.5406 0.7651 0 0\r\n"
				+ "M  V30 15 O -4.3209 -2.9076 0 0\r\n"
				+ "M  V30 16 C 1.6151 2.9941 0 0\r\n"
				+ "M  V30 17 C -1.4455 2.9759 0 0\r\n"
				+ "M  V30 END ATOM\r\n"
				+ "M  V30 BEGIN BOND\r\n"
				+ "M  V30 1 1 1 2\r\n"
				+ "M  V30 2 1 1 9\r\n"
				+ "M  V30 3 2 2 3\r\n"
				+ "M  V30 4 1 2 7\r\n"
				+ "M  V30 5 1 3 4\r\n"
				+ "M  V30 6 2 4 5\r\n"
				+ "M  V30 7 1 5 6\r\n"
				+ "M  V30 8 1 5 8\r\n"
				+ "M  V30 9 2 6 7\r\n"
				+ "M  V30 10 2 9 10\r\n"
				+ "M  V30 11 1 9 14\r\n"
				+ "M  V30 12 1 10 11\r\n"
				+ "M  V30 13 2 11 12\r\n"
				+ "M  V30 14 1 12 13\r\n"
				+ "M  V30 15 1 12 15\r\n"
				+ "M  V30 16 2 13 14\r\n"
				+ "M  V30 17 1 1 16\r\n"
				+ "M  V30 18 1 1 17\r\n"
				+ "M  V30 END BOND\r\n"
				+ "M  V30 END CTAB\r\n"
				+ "M  END\r\n"
				+ "")
				.mrvFile("These are missing vales for now")
				.molImage(" PNG        IHDR   X   X     ¾f Ü    pHYs  !¬  !¬ ÖL*+    zTXtmolSource  x  VÛjäF ý F/~É´ªúÞÆãeÙM °&!» ¼ ¶Fö F ÑÈ·¿Ïi ×Ì ¬À AL«ú ªSU}ÑÅ çv+ ëa×ôÝú %  º«úMÓÝ­ÏþþöÛ* }¸¼¨  °Û­ ïãx ^ OOO²ú^·é¹ïdÕ·Å~þüy× a ´ì »R qùÏÕ ¯ gÕt»1uU Ö®9ßMÆ/} Æ¬b)DÙ¦á±éÊ=¼l Ç½·kv×¤®ÙÊçÝ¦xK¦ø É à Ûf[ Û~hÓ( ÙIR í/â®îê! õFÜ¼À ¤ T\\^\\}î« ¶îF ³ ¦JÛ¯ãðPÁÐöÛºz 7 ~ÿ¼.Z ! }ûq Ò È£lN,  I dD²\"9 ¼HA¤(   Ì3    ÂÀ0@ì Qoë üÛË}  Äáï ù[!¶}Wÿ  a] 8ü©ù ª­  d´òDA gCô¤­`ÉZQ V±&gÈG¡¤£è½wÁ:r¬­ Z:¯¼vÁ{ Î*ë`R jvV % }öå Å,¹è rAYA  ¦` ñ Ù    /8¢H&R (VX} z£md  N¬@4   ò¢QAs AB´ I^  álõ  Ø 8Îù¨5 R 6%­!§ ¶È   Ü¹   -+G:f G  j µÊ   ò^ â å  ÉÂ`¢2!(ô ¤vÞZ  *¶ Å$é R 5È ,ó  JÂÆ² × Ð]  1M  Q¨¯Ò! V6Z8c ¬ aÙ¬rÃ * ¨e   q1 ²Ú (V  ß  ©Ê£Å    ÔS F,  h<¤N    ª   j¯U ¬µ2  å&y TÎÌA0Z \" g^ À}nX  Á  4 MÞF£}   â ååÅMßm¦½´  f³.n¸ vÖ_õíN½n®BôÃ¦Æ¢ç ¬=TÍ q ª ¡y¿¾AÕ1ÔÌ ~É«= æ#` ê ¡ù¨X à ¡ùHYò fÐ°  ÇPw Ö  ¦cìt - y ^¬  ¶l:  Ñ']Û ¤ BN ·?s }ÛwÐv }Ò¾ýY¾¨ÄÏ &/¶ Ã |²àÊ  Rþ¸ 0 ÝXW¿bn úîSß ©Á 7ÅèsÍªêáþõÊ n !m2 oÅ ñMJ¾îd^M æ  õt  ×'kúyF9W·¬Xÿ_Å¹ ?)ù?(ïj. > J|,]þ £  Õá{&?  U IDATx^íÝ Ø Uþöñÿ® *] ¥ Ò  maA Qi lkÅ^±b/X°ËÚ Ø»Ø°£\"ØÀ  +\"\" Å  \" ý¼Ü³oâÌÉ < Ì$  |?×5×û 7gâÃÔ;3çüÎÿ     êÿìÿ    Á °   BFÀ          d ,     °   BFÀ          d ,     °   BFÀ          d ,     °   BFÀ          d ,     °   BFÀ          d ,     °   BFÀ          d ,     °   BFÀ          d ,     °   BFÀ          d ,     °   BFÀ          d ,     °   BFÀ          d ,     °   BFÀ          d ,     °   BFÀ          d ,     °   BFÀ          d ,     °   BFÀ          d ,     °   BFÀ          d ,     °   BFÀ          d ,     °   BFÀ          d ,     °   BFÀ          d ,     °   BFÀ          d ,     °   BFÀ          d ,     °   BFÀ          d ,     °   BFÀ          d ,     °   BFÀ          d ,     °   BFÀ          d ,     °   BFÀ          d ,     °   BFÀ          d ,     °   BFÀ          d ,     °   BFÀ          d ,     °   BFÀ          d ,     °   BFÀ          d ,     °   BFÀ   ³páBsÓM7 ÝwßÝtêÔÉ4jÔÈ¬ºêª¦^½z¦uëÖfç w6 \\r  1c ½ªÇÿýßÿ¥ ¿ ¬ Ä G6b'È 9Èº~ u  Â²hÑ\"3tèPS³fMÏ¹ kéÕ«  8q¢ýU  çS u 8àÈFì ¹0 Y7_aßÄ 0|øá ¦E   Ç^¾KUªû<  ë qÀ  Ø ra ²n> q   úè£ LíÚµ=Ç Bý¨Q£ cV? ~ÿýw³dÉ 3{ölóÄ O aÃ  öíÛç<6  »AÖ â # ± äÂ dÝê ë&  ±lÙ2Ï1¦WÕÏ<ó Ý,«W_}Õl½õÖöÿì r> Y    lÄN  s us)æM  â¬³ÎJ  k­µ  9s¦Ý¤`AÎ§ ë qÀ  Ø ra ²n.Å¼   Rð¯_¿~úØ|ä Gì&  9  ¬ Ä G6b'È 9ÈºÙ û&  êþûïO  ]ºt±? ,Èù d]  8² ;A.ÌAÖÍ¦Ø71 PC  I  ÿýï í   r> Y    lÄN  s u³)öM (Ô& l >6ÕÏ/lAÎ§ ë qÀ  Ø ra ²n6Å¾   jÖ¬YúØüòË/í  s OA   8² ;öÅ¹Ð%,Å¾   Z}õÕÓÇæ  +ì  ³Ï©B   8² ;öÅ¹Ð%,Å¾   *ö±i S .@ qd#vì s¡KX }   Uì§«AÎ§ ë qÀ  Ø ra ²n6Å¾   *vÿÀ çS u 8àÈFì ¹0 Y7 bßÄ B qÄ éc³ #\\  OAÖ â # ± äÂ dÝl }   Uì mAÎ§\\ëN >Ý  1ÂôéÓÇ <}µÕV3  64ýû÷7cÆ ±    yd   ëÂ\\ lë ¹  û&  ªØ³ d; ò k]÷gU-ûì³ ùóÏ?íÕ HÉ<²  Ëua®N¶uí ¸½äº  û&  qæ g¦ Í°çÉÌv>å#×ºÝºu3£F 2 |ð ùñÇ Í _|an¸á S§N ô:×^{­½  ) G6 q¹.ÌÕÉ¶nÐ z1ob@ ?üð i×®]úølÔ¨  ?~¼Ý,+õ+Üf mìÿÙ í|ÊG!ëÞzë­éuºvíj  DJþG6   \\ Sü® ï ½ 71 ¨ >úÈÔ®]Ûsü÷êÕË\\ ýõæÃ ?4ß}÷ ùý÷ßÍÒ¥KÍìÙ³ÍØ±c   íÛ·Ïy¾ø= Ü Y÷ë¯¿N¯£ò(@ å d  QÈ 9Åïº~.èÅº  aÐÓÙõÖ[Ïs|úYªRÝç¹ ²®Î¡Ô:ú· Q ÿ  DD! æ ¿ëú½  ã&  åÛo¿5Ç w ©Y³fÆ± méÝ»· 8q¢ýU  Çn!ëº  h u Êò?²  (äÂ âwÝB.èaßÄ °-\\¸ÐÜxã f·Ýv3 :tpFÌ®ºêª¦nÝº¦uëÖfç wvFÕV× ÐïùäægÝo¾ùÆÜsÏ=Îß©ö n¸¡óã  ²ê l bü\\ mù® Æ =¬  P©Üç« ¶mÛ K.¹ÄüüóÏvS rrße  Ã  qôÕW_ÙÿS\"Øç£úA  8 R(    àÂ  q¢à¿ÓN;9ÇêÆ oì  I\" È _|Ñ  0 }nî¹ç æ·ß~³   AÀ ª   ºúr uÔQÎ¢W  ÛòåËÍÖ[oíùAðÔSOÙÍ ç°Ã Kÿ{   n  D   ¨F¹.è  rHú¿Û¹sgB Òô¤jË-·ô +õíS Ü¤S  Ô¿¹eË öÇ@d ° j ë ¾ß~ûyn êh¿hÑ\"» *  ÚöìÙ3#\\M <Ùn H¿þúkúß]£F ûc 2 X@5ÊuA 7o^FM-õ³Y¼x±Ý  âûï¿7Ý»w÷    óÍ7ß´ & &fOýÛó©M     ¨F9/è*áÐ´iSÏ u³Í63K ,± \"áT&dÓM7õ   ÷rÊ )vÓDs?ÙÕkt ª X@5Ê}AÿôÓOÍºë®ë¹±þûßÿv^ ¡2hÀÃF mä9 4ß¥f H ½ ¿ôÒKÍÛo¿íÔ ÓôRúAñÂ /  wÜ1ýï×´TsæÌ±W \"       ú´iÓLãÆ =7ØÍ7ß<±Ãòñ7Í ¹Á  xöý:ë¬c¦N j7M ÷¿3ÛÒ¬Y3óÚk¯Ù«  BÀBÅ³/ÞU-Q¸ k2i=µpÿ] bGÃõ L ü ;vôìó&M 8¯­ jÆ  æ +®0ýúõs ý®¹æ N=:  Ûm· ¹á  øa X `¡âÅé þÞ{ï9ýnÜ7\\ÕB¢Ò|ò|þùç¦]»v }Ý¼ysçx  } , fÞyç gä ûÆ«p¸bÅ »)bJ#H5_¥{ ·hÑÂÌ 5Ën  ¢ X@ iX¾j ¹oÀzÚöË/¿ØM 3ª»¦zkî}ÛªU+3wî\\»)  #` 1¥Â êxï¾   4¨¤Óù \\ }ö ó Ð½OõÚzÁ  vS  GÀ bìå _6µjÕòÜ wÝuWg$$âE ×í g :tp:º      s  z 5ÖðÜ ÷Øc óÇ  ØM Q  ü±SzÁ½ 5ÿ¤&   O ,   }öYgä£û =xð`BV ¨XèÚk¯íÙwªÍ¦â¢ â   $Ä3Ï<cjÖ¬é¹Q pÀ æÏ?ÿ´ \"\"Þ}÷]Ó°aCÏ>Ûd M iq Ä   H ' |Ò  Ú}ÃÖô> ýõ Ý eöÖ[o    xöU·nÝ    Ä   H Ç {Ì¬ºêª  ÷ !C Y òúë¯ zõêyöQ  =ÌÒ¥Kí¦ b   $Ð 1c2BÖ1Ç c7C ¼òÊ+¦N : }Ó«W/³lÙ2»)  #`  uß}÷ UVYÅs#?á  ìf(¡  'f ÕèÓ§ óI  DÀ  ì®»î2ÿüç?=7ôSO=Õn  xî¹ç y.Ýû¢oß¾æ§ ~²  H    p·Ýv[FÈ:ë¬³ìf(¢qãÆe Ñ 8p óG  FÀ *À 7Þhþñ  xnðç w Ý E0vìØ ò ;î¸#óF  GÀ *ÄÈ #=7y- _|±Ý !zôÑGÍj«­æÙæ Ê ù\" ä#`  äª«®Ê Y ]v Ý !xðÁ 3Frj #æ  *   ¨0 ^ziFÈºúê«íf `ôèÑ #8 º ¨, ,  ]xá  !kÔ¨Qv3 àÎ;ïÌ W  x S      T¨áÃ {B :Áß|óÍv3øpË-·d Ø<ì°Ã W@ \"`  lØ°a 0 ppûí·ÛÍ  ë¯¿>c¤æQG Å E@ \"`  î SNÉ Y÷Üs Ý 9\\sÍ5 ájèÐ¡v3     À  üñ p >D÷ß ¿Ý UÐ(L÷¶ÓròÉ'ÛÍ T    ÇÑG í  *1ððÃ ÛÍàrÑE e +½v      ú  qÄ  ° \"  ?þ¸Ý + {î¹ áJ   @ X Ò ² >ø`Oh¨Q£ yê©§ì¦ Í   EO³     ÀC% öß  OxÐ\\zãÇ · V$õ¯²Ã Õð Ø X 2¨âøÞ{ïí  k¬± yî¹çì¦ E# ípE | U!` ¨ BÖî»ïî  k®¹¦ 8q¢Ý4ñôêT5­ÜÛBe ¨~      ¬41ñ.»ìâ  µjÕ2¯¼ò Ý4±ôÊTÕØÝÛ@µÂ¨z       ~ýõW³ã ;z F :uÌë¯¿n7M  +Í#èþ·«FØ wÜa7      jýòË/fàÀ   Q¯^=óÖ[oÙM C¯H     ®F  m7    , yY±b éÛ·¯'pÔ¯_ßL 2Ån {z5ºÇ {xþ­*¼úà  ÚM  J , yûé§ L >}<Ác­µÖ2ï¿ÿ¾Ý4¶~ûí7³ë®»zþ *¸úÈ# ØM  +   _ /_nzõêå    52S§Nµ Æ ^   4ÈóoS °±cÇÚM  '   ß -[fzöìé \"  76 |ò Ý46ô t»í¶óü V_}u3nÜ8») T     K .5Ý»w÷  u×]×Ì 1Ãn yzõi÷/SÍ¯g }Ön  y!` (Ø %KÌ¦ nê &Í 533gÎ´ F ^yÚýÊTë«  ª     @   /6 m´ ' ´hÑÂÌ 3Çn 9zÕi÷'«]»vE R P  ,  -Z´ÈtéÒÅ TÖ_ }3oÞ<»idè §Ý ¬nÝºfòäÉvS ð     ß|ó éÔ© '°´iÓÆ|þùçvÓ²ûþûïM·nÝ< k    ]8 @i °  æË/¿4íÛ·÷  víÚ9ÿ{T|÷Ýwf M6ñü   64ï¾û®Ý   FÀ  ª/¾øÂyrå 0 ;v4_ ýµÝ´ä¾ýö[ÓµkWÏß¶öÚk  >øÀn    °  nþüù¦U«V  ³Á  8 §\\ ð:wîìù ÖYg óñÇ ÛM  0   ¢ ;w®3 Ð hôôH¯èJmáÂ ¦C   ¿¥I &fúôévS     @ÑÌ 5Ë4oÞÜ lT7K ÌKeÁ  ¦mÛ¶ ¿A Óg }f7  Ð °       ¹  Fð©LB±é) ýª²eË föìÙvS     @ÑéU ú;¹ N  = B Å¢ ¥0åþo¶nÝ:Òµ¹ $   @I¨3¹Fì¹ Ï [láLU 6=5³_Mª\\ ^  @) °   Ê!¨æ ;ølµÕVÎdËaÑÓ2û ¤:¸«£;     @I© §ª¦» Ð¶Ûnk~þùg»©oS§NÍx ©ò Q¨Á  ² °  ÜÛo¿mêÕ«ç B   0¿üò Ý4oz:f¿ TY rÖÞ P¹ X Êâ 7Þ0uêÔñ ¢ vØÁüúë¯vÓjM 2%ãÕ£ÊA £æ     @Ù¼úê«¦V­Z `´ÓN; ß~ûÍn  &h®_¿¾ç;T ¢ µ¶ ÀFÀ PV &M2k®¹¦' í¶Ûnæ÷ß · f <y²©[·®gÝ ={ ¤Æ  äBÀ Pv/¼ð Yc 5<Ai¯½ö2 üñ Ý4íå _6µk×ö¬Ó«W¯¢ÖÖ  | ° DÂ   Ìê«¯î Lûí· ùóÏ?í¦æÅ _ÌxµØ§O ¢ÔÔ  B ° DÆÓO?mjÔ¨á N  t 'd=ûì³ O»úõë j--      R xâ ³Új«y Ôa  fþúë/3nÜ¸ §\\Ûm· Y±b ý5 PV ,  óÈ#  UW]Õ ¤   hjÖ¬éùß    ¨v      @$=ðÀ f UVñ *÷¢  ~Ê9 @) ° DÖèÑ£Í?ÿùÏ pµç {æUÆ  Ê    ²ôª°ª 5tèP») D   @$UÕ Ë½  üñö*    ,  SU¸R vûi ! @T ° DJUáê   pjaÝzë­ , ±@À   ¹ÂU ! @  ° DB>á*å [n!d  4   ²ó ®R Y ¢    ¬  W) , QEÀ P6AÂU ! @  °  E á*    j X J.Ìp RUÈ:á  ìf P  , %U p BÈ    , %SÌp BÈ    , %Q p BÈ Pn , EWÊp BÈ PN , EU p BÈ P. , ESÎp BÈ P  , E  p BÈ Pj , ¡ R¸J!d (%   PE1\\¥ ²     @h¢ ®R Y J     q W) , ÅFÀ  X ÂU ! @1 °   Çp BÈ P, ,   s¸J!d (     $!\\¥ ²     À·$ « B  0 ° ø Äp BÈ      ¼%9\\¥ ²      /  ®R Y  \"` ¨V% « B    X rªÄp BÈ P(   ¬*9\\¥ ²      J «¿ ² øEÀ   p    À     ÂUv , ù\"`¡¤fÏ m 7onV_}u3hÐ óè£  ¥K ÚÍP& «ê ² ä    ùî»ïL &M<7&-ºYýûßÿ6g u  8q¢ùå _ìUQ  «ü ² T    X¼x±Ùxã 3ÂUUË k®iú÷ïo.»ì2óÞ{ïÙ_ \" \\ùGÈ     E§pµé¦ znD5jÔ0«¬²JF¸ªjiÔ¨ Ùk¯½Ì­·ÞjæÎ k = \"\\    =?ÿü³ 4i ùÏ þcvØa ³páB» P  , Õ÷ß o6Ûl3Ï hÝu×5Ó§O7Ë -3O>ù¤9î¸ãL§N 2 U¶¥M 6fÈ !æ¡  r¾  #\\ GÈ*¯/¿üÒ9 µÍ»wïnV[m5Ï¾hÐ  ý<Q  , Í %KÌ¿þõ/ÏÅn uÖ1 |ò ÝÔ¡_ wß}·s oÚ´iF°ªjùÇ?þáü7Î8ã óüóÏ  +VØ_ , Wá!d Æ_ ýeÞ ÿ}3jÔ(3xð`³þúëg\\ ªZ6ß|s³|ùrûë ¢\"`¡(ô ±[·n  \\ãÆ Í´iÓì¦Y©íu×]gvÜqGS§N   fU F'n»í¶fÄ  æ wÞq.ÈÈD¸ ßÍ7ßLÈ Ù ?ü`&L `   núõë ÷u ªE× ~ ¡  X  .   è¾¸­½öÚfêÔ©vÓ¼ýþûïæµ×^3ç  ¾ÙrË-3^ d[ôz` =ö07Ýt  5k ýµ  pU< ¬`t   =Ú uÔQf£ 6r PÛçt¶E}:7Ùd §ËÁ  <à ³µÖZËÓF}²~ûí7û?     ¡R¸êÑ£ ç¢¦põÑG ÙM ùñÇ Í¸qãÌ ' hºté q±Í¶è Âa  æ\\  -Zd mâ ®     _ ýÕ¼ñÆ æÊ+¯4»îº«Ó}À>_s-õêÕ3Ûm· ¹à  Ì /¼à\\ lo½õVÆS/ýàúã ?ì¦@è X  :­÷ìÙÓs1Ó À ?üÐn º¯¾úÊÜ{ï½æ   2ë­·^ÆÅ8Û¢_¼§ zªyöÙgÍO?ýd m¢ ®J   I?h xâ ç|Ûb - ×ùöù kiÛ¶­s¼êi´ kÊË/¿ì ~q  ® t @± °  ýzÔEÓ} kØ°¡Ó!µ >ýôSsýõ× ]vÙÅÔ­[7ãb]ÕR³fM³õÖ[  .ºÈ¼ùæ     «Ò«ô ¥. Ú   x iß¾}Æù kÑ¹¨ é' |²yüñÇÍ·ß~k ½/z]¨ïtÿ7 >úh»  *   Óè ^½zy.^êû  \"¡z  W  ^x¡éÓ§OÆ 6Û¢W »í¶ ¹á  Ì  3ì¯  ÂUùT ²ôZ;it xñÅ   'Ûo¿½©_¿~Æù kQ7 ý ºâ +ÌäÉ  2    }  vÚiv3 4 , ¢ «: »/ZêXþî»ïÚM#C¯ õ ö SNq:ÒÚ ûlK  -ÌÁ  lî»ï>óõ×_Û_ I «òKbÈZ°` yðÁ ÍÐ¡C \"Âù  N-]»v5G y¤S eæÌ ö×  º ØûB g b `¡` *[mµ çb¥p5eÊ »i¤éõÃý÷ßo =ôPÓ²eË  A¶eÃ 74' t yæ g\"Yc p  q Y Á«sú k®1{î¹§3Y»}.äZj×®í H8ç sÌøñã  0å¤ eöèDu´ ÂFÀBA ®ôºÍ} Òk ·ß~Ûn ;úE}ã 7 Ýwß=ïW  ú§wïÞÎ &½â(÷(%ÂUôÄ%d©@ðÓO?mÎ<óLç ¯U«VÆñ kÑ  }öÙÇ©a§n Q<æ®¾újÏß¬À¥ý     ß4××6Ûlã¹@©¿  D' F )4^rÉ%Î¿9ß O  ¾ÓN;  #Gf­\\_, «è bÈÒ  ;î¸Ã ~øáf  6È8 s-ªG§  Ôyÿá  6_|ñ ýõ ¥> î  ö jp a!`Á  +=îw_ 4JO£î* þýÏ=÷ 9ýôÓ3æXÌµ4kÖÌ MuÏ=÷8s§  á*úÊ ²TÉüÕW_5 ^z©Ùyç   ¾ö± kQ  A  9?8^zé¥Ø 6Ñyìþ÷éÜyì±Çìf@A XÈ .Î ®Â}AR¸Ò ½Jµxñb3fÌ sÄ G Ö­[gÜ ²- ;wv: ?õÔSU H, á*>J ²T îÑG u¾[³+èU¶},æZ4 û!  bn»í¶ ? - c 9ÆóoÖ6R¿J (  ò¢pÕ¿  Ï H¯ÁÔß(   T   &J Ù³g; hÕ 8ß§  E*sqî¹ç:O   Ê p ?a ,½Îþà   úoûî»¯iÕªUÆ± kYc 5 ~  8}ìØ±æûï¿·ÿ  ¤í¦ÑÁö¶ 4i Ý ð   j©&ÍÀ  =    ÒÜ AÜ~ûíé  : ú  :.4úJ¯côäO mû¦VÕ¢m«9ÓÔ ÷ã ?¶¿2 á*¾  ,Í     ÌõãÇ  ¦º¥iÓ¦æ?ÿù s © a!Á>)4(e¯½öòl mÏJéú â `!' +Í÷å¾ð( ¼òÊ+vS_î¼óÎ  KT      ª ã°aÃL÷îÝ3þýÙ u×]×ì·ß~æ®»î2  þ¹ç; Wñ oÈÒÓQÕqR r¿ !ëû7Þxcsì±Ç:uÜæÎ k }ì¤¦ÞÑy F]: Lõ/so7õ9+×l  ?  ²Òd¬z â¾àhÈ¶æö B¯ íÂ  4 H  Ã+ ©àb»ví2n Ù   ;:ýF4  á* ª Y* rÕUW9³ (dÛÇA®E}#õÔYE4  þùÐúù  & Ï6õ Î 0J£èGPß¾}=ßÝ¸qc3}út»)P-  ª¤pµã ;z.4 WAû%h   ®ô« ÆÌ 7Ïymª>i :Ä¾iV· ®â­ª  ïÒ¦M ³ÿþû;õÛüL  U©©wTJASï¨  ýov/Úna 0Õ Û WU£ õ  ð    zT® Nî  f£ 8q¢ÝÔ ½Þ°Ã  ®f§ Ë _~¹ó$¢ºb  «dPÈ²÷­½h.Í ={:³ h~½o¾ùÆþ Ø ? ~ÁSïè)®:æ iéÒ¥ eX4hÀ~E äBÀ  Â &]u_X ®ôk2 MEc_4õjL#xP==QTÝ¡³Ï>Û¹¹ºûßèÿV  $ ý´¦Q£FÎ9©°­ %z  gq zç»ï¾3]ºtñü·;tè  @ Ò `!M ¾]wÝÕsAÑÈ7õá B¿Lí¾Bª E¸*ÜC =äÙ ª¢ ø :uªg¿ wÜqv Ø sê u8/å ZÕ ³ûGj¢jÕ¿ ªCÀ CáJC¶Ý   +   BAÀ W  v á* uZv   2d Ý 1tñÅ {Î 8ö§ :õN·nÝÒSï,\\¸Ðþú ÓëK{ x  V       p¥ Kî  æÜ 0a ÝÔ ] ípuè¡  ®B²å [¦·«:9#þTm=µO×_ }ûãÈÑÔQ*Ù  zg­µÖò ïÕ-©©wF   é©w4 | &M< » ²êß dCÀªp Ú¬~ î  ÂUÐ©\"4=  ®T-¹  ÷ NCðÝÛwÎ 9v Ä ^G¹G Fñõ`%O½£¢¿ê çþ÷  0À©  T  UÁ ®öÞ{oÏ C# Æ  g7õE# ô¨ßý½ª]C¸  :<»·ñ­·Þj7A hÿ¹÷gÐ¾ A  zG c 6õÎ»ï¾kê×¯ïùwj  Þ  6 V R¸ <x°çB¡p¥É  Pee;\\©> á*|º¨« dj;kª Ä » ¸F  zê 0¦ÞQW ¤O½£ùWí úº r    U  ®ô Ô} Ð£þ' |Ònê Ö·_ h  .<Åã¾)«8)ýÛâI} ôÄ'µ/K  U8sôèÑ §ÞQ  $L½ãÇ /¼àt¥po uêçü   «Â(ìè  ûÂ P¤'OAèÉ  ®ô«. é+ Ýµ×^ëÙæI Ï1©ô Í½ 5_` TGM  kê  baê àtÍ³ ÖWâ _È  UA ®Ô Ê}AÐ B}¦ P -½^t ¯úv ® oÚ´i í~Ùe ÙM   ]ë>' öWR Lýh:í´ÓL¯^½2 ¶T·$mê bQ  » ²   BÀª  W Åç¾ h  F  ¡Ñ v¸Ò¨DÂUé¨ïKjÛ«ï âEç¦û ÒÖ[om7ÉË _|áÔCSµq÷ùXÝ  zG  'eê RºóÎ;3æ TÙ   U Ô/@C£Ý   «G yÄnê êdÙ¿ ÕÉ pUZîW¾êÇÃ°ñxyã 7<ç :  B¯ðÜß mQ_½ÔÔ;ê°Íñ  FZÚÛyäÈ v3T  VÂ)\\©rºûÄW¸Ò£í 4ÚÈ Wª ÏpåÂè5  Kêæç·zþÝwßíÙ A'åFi  6Ì³ÿÔù¼ [l±EÆM^Ë  nè<Ùºë®»Ìg }f¯  ¨Øª{»kÐ *Ú£r ° LáJsþ¹Oz +Í   :¹j  ÷÷ªã,áªpî>8  å ^ ¹G iÎ7Ä {BaM-S(M ¬§XZÔ H¯ï .]j7C i j÷uQý³ ^o _ ¬ R¸:òÈ#3Nv © BÃ íp¥× I­yS*§ ~zz{*,}ýõ×v  :wî ^_ ¶  zZå> ô4 ñ¦*÷î}ªA AKà   X uÔQGe « C¿õêÉ]«GËN;íD¸ ÁsÏ=çÙ®~÷ÕÐ¡C=ûzÉ %v D ú[¹÷ûë¯¿n7A Ùo Ô ¢Ü ùQz ¬ :æ c<'·n¸*( Ä¤I 2Â  \\ª¾  Ó¤±î>m ñé ~!»÷Íc =f7A iÄ`j ­³Î: åM íG Yv  ªþ®é­P9 X £ bíp¥NÐA¼üòË SCì°Ã  « m»í¶éí»ÞzëÙ çôÃ ?x&×VunD j]¹ Uª   C£©wÛm7ÏuSS ©¯ *  +A ?þxÏÉ¬Ú, 9 Ä+¯¼bj×®íùÞí·ß ¡ÝE Ú9îíüé§ ÚMrÚ|óÍÓë¶oßÞþ  £×Àîý­jîH ý µËg4lØÐL :Õn  \"`% Ý±Rá*è a=Î¶Ã .  «âÐ ¹îm­Ú:~  >Ü³þüùóí&  Ív ÚW 8¢ù  <zý¿ÕV[yÎM  ¥dFò ° @  Ý'¯ÂÕm·Ýf7óE  ëÔ©ãùÞ     +VØM  õÛXk­µÒÛ[¥/üÐ«\\÷þ  °Q<  ¢×E©}¥þ H®eË 9£{Ýçg  -Ì¼yóì¦H  VÌ zê© áê [n± ù¢ L ÜÕý½ýúõ#\\   µ¦¶yýúõ}UÅ×MÛýÄQ m# 4¢Ì}~Ýzë­v $ úÜ©Æ {¿·mÛÖ|ùå vS$  +ÆÜµ ´¨~ÒM7Ýd7óåÍ7ßÌ W}ûöu s£ø4¹®{Ûk ø¡þq©u5*MõÐ =î² úQôÕW_ÙM @ß~û­éØ±£ç WqÙE  ÙM   ¬ ²§×P¸ÒÍ9 ·ÞzËóÚBË6ÛlC¸*¡ 3gz¶ÿE ]d7Ééª«®ò¬¯)x =  )µ ( [Y4óBëÖ­=çé¦ nJÕý \"`ÅÐYg å99 ®üv ¶© µ^I¹¿W5zèx[zî o >}ì súè£ <ûðÊ+¯´  Ì zÝûÈo FüÍ 3Ç4oÞÜs h ðòåËí¦ 1 VÌØs])\\  µý wÞ1  4ð|¯F½ ®ÊÃ=/aÍ 5}í ½ Ô ¥ÔúÛm· Ý evá  zÎ5 bT  aiÜ¸±çXP-<úº&  +FÎ=÷\\ÏÉ¨åÚk¯µ ùòî»ïf «Þ½{óKª  xà Ïþ 0a Ý$'unO­«Nï    îÝ»§÷O«V­ì QA>øà ÏÈa-*âÌôcÉ@À  óÏ?ßs jÑ<fA¼÷Þ{ 'w¯^½ We¦ °z2 Ú'§ r Ý$' gpïS o@4hÄ {ßª80* ú¾Ú%qöØc _#  M ¬ °_)h Ú·æý÷ßw* »¿s -¶0?þø£Ý eà Î½É& Ø ç´`Á Ï~ÕkeD J¨¸÷Í /¼`7A Ò  {®×  : QÀ1GÀ ¸ /¾ØsÒi¹üòËíf¾è±t£F <ßÙ³gO§  ¢AO­RûFÃøõTË   :xö-¢aÇ wLï  *áU RÔ @}.Ý×eæ  7 V ÙsÓi¹ôÒKíf¾¨C­ ®zôèáL  èÐÅÖ½  |ðA»INÇ sLz]M Í ðòÓ` M  Ú/ûì³ Ý  îñÇ ÷LÚ®å´ÓN³ !& X ¥ å>É´(p ¡ F×^{mÏwª  á*zt3vÿ =ì°Ãì&9éBíÞÏL$\\~O<ñ g Ü ÿýv ÀÜ{ï½ÎSk÷±¢>¸   V é  ûäÒ¢W A|üñÇ C 5  ' Ñ¥ X©}¥ÚX~,Y²Ä¬²Ê*éõ ;î8» Jì C Iï ÕV[ÍÙG@UÔWÏ= BKÐ~·(= VÄè$²Ã :¹ 1mÚ4gÚ ÷wþë_ÿâ  q*@éÞgªòî {rÙN :Ù £ 4 ·û  fH rÑ(q÷ù¯ÀuóÍ7ÛÍ a ¬ ±O(-A  O >=#\\m¶ÙfÎÄ£ ¶7ÞxÃ³ßüÎ3é®ø¯ ³¦è@yL <Ù³/¯¹æ »  Á A®W £G ¶ !¢ X ¡ ¡î I    ¡JÁîªÞZ4ç á* T Ç=}Ñî»ïn7ÉiÒ¤I } ×]wÙMP\"g q g_hª   § ~ºçØQ'øÇ {Ìn  \"`E {j Ô ´vÑ  3L &M<ß©zJ  /¶ \"ÂvÙe ôþSQX½jÊ *¸»këì·ß~v  HçÎ ÓûaÃ 7´? rr  ÖR£F 3~üx» \"  UfóçÏÏ Wzµ Äg }f 6mêùÎ 6ÚÈ|÷ÝwvSDÜ¨Q£<ûQóFú1`À ôº Ü(=õ sïÃ3Ï<Ón ä¤ £  |°ç8RÉ =¥Ft °ÊLSg¸O nÝºÙM|ÑÅ¼Y³f ïìÚµ«Y´h Ý 1 ×¼î}é·T =\"U£IQZW]u g ¼ùæ v  Zê2°×^{y %M±Ãñ ] ¬ hß¾}ú éÒ¥ ýqÞfÍ e 7oî9 õ: p oë­·^z n»í¶öÇ9iJ$÷ñ@çêÒs ÛP H¦?A¡Tù Ð A sºA  Îy è!`E » ¬F{é©V!4:Ð}â)¬ù b Ñ£9ÉRûtõÕW7+V¬° d¥ ¹»¸¬¦jAéh@ »2·ß ± Mç ß¾}=×úZµj  ^zÉn 2#`E &|u ,  Ãu 8S§Úo¾ùÆn  Reg÷ññüóÏÛMrÚ{ï½Óëê  óß  ½ï |òI» àÛòåËÍ [lá9¶ôã ÑBÀ  ý\"qÏQvÀ  ØMòr÷Ýw;ý¯úõëg¾þúkûcÄÔW_}å©ê¬aÛ~ÜvÛm  ñ«¯¾j7A ì¹ç éí®  ?ÿü³Ý ( fáhØ°¡çÜö3Ê ÅGÀ  þýû§O  $ÀM}éRÇ ^ û1wî\\ÏEø¼óÎ³   T&£nÝºéí¾ÓN;ÙM  )L5jÔ(}|m°Á v    +\".»ì2ÏMPÓÛ )' xbúØP5g¿%7Ú¶m ^¿W¯^öÇ( ç {ÎsNëI\"  {¦  «F´ °\"â½÷ÞãdAVãÆ ó   ?ü°Ý$§# <2½®& ^¶l Ý !;öØcÓÛ\\¡ ×ö ÓÙg í¹&h 9¢    öh/ Å R~üñG§zsêø 2d Ý$§G yÄs1~úé§í& YË -ÓÛ»G  öÇ¨po¿ý¶óÚX³v¨Æ _  #u|uìØÑþ  @À  w 9F{Á¶å [¦  6mÚØ ç¤) VYe ôú' p Ý !úà  < ö K.±   ©ÿT  -ÒÇÇ wÜa7ÉI ·» ¾ rÊ)v D  +B í \\Î?ÿ|ÏñáwÂ`Í  Z7HA[Tï  .ðì+*èÃMÕ×ÝÇÇ 7Þh7Éé [nñ¬Ï 9ÑDÀ  yóæyN sÏ=×n  6yòdÏñ¡ ¬ a ´EõÜa¶uëÖöÇ¨pz-è> ýö Ò«ÅÔºª øûï¿ÛM   ¬ i×®]úÄQ!9 E Q÷° ÕXò#¬ ¶ÈmáÂ  ×7¼  mÓM7M  ~ûO©n¢*· z @é °\"æ¨£ J 8 íõÃ ?ØMPÁÜó ©   yíì ¶  x Ý !¸é¦ <AvâÄ v T0»ÿÔÉ' l7Éiüøñ ãK ¦ M ¬ yôÑG=' SkÀMå;ÜÇ Ê{øá ÃL  #|Ûo¿}z k\"^^ßÀÍî?å7 »Ë hà óÍF  +b49¬{´×qÇ g7A S Z÷ÅY jýøï ÿëY úôév   9â4'\\jû  <Øn  ·óÎ;§  zõêù -ÞªU«ôú={ö´?F  °\"èßÿþwú êÔ© ý1*\\Ó¦MÓÇ ¦XòcÊ )  5räH»  xì±Ç<Û÷Á  ´   ýòË/ þS{ì± Ý$' Fu _ ]t Ý  BÀ  ³Î:+} é]½ÞÙ )  <u|h a]´ó¥ú;î bõk á9è  ÒÛV a5!/ 2aÂ O@òÛ êÒK/õ¬ÿþûïÛM ! ¬ RM ÷It×]wÙMPÁî¹ç Ïñá·  ~5§ÖÕ+ ú  CáÕ=  ú» nêò :>4} ßþS½{÷N¯O Êè#`EÐ¯¿þêy ¼ß~ûÙMPÁì2 g y¦Ý$§ o¾Ù Ð^ ýu»  ðÚk¯y¶ëu×]g7A s÷ ò;} úç®ºêªéõ 8â » \"   Q   L Hë®»®¯áøH¾Î ;§  îÝ»Û ç¤¢ î pá  ÚMP ÓO?Ý³]çÎ k7A ³ ¨ø=ï xà Ïú 0 > VD]yå   é£ >²     :4}lhÔ©~Ýúáþ%½ÕV[Ù £    Ú¦]»vµ?F Ó _÷5Ýo  ½ÉH­« ª?ýô Ý  CÀ ¨ ?üÐs2^uÕUv T0ýzu   ½æÇá   ^W ±U^  ûì³Ï<ûãì³Ï¶  Âé LêøhÖ¬ ¯·  üñ gpÊvÛmg7A  °\"J'ß:ë¬ >¡T¼ HQ  UúO  G }´Ý$§1cÆx Á3Ï<c7  W\\q g{¾õÖ[v T°%K  ê?e÷ï 5j Ý  DÀ 0 )L Pµk×v:¿ ) «2u|´oßÞþ8§E  9£ Rë tÒIv øà~:Ñ¤I _O' |vÿ©±cÇÚMr 6l g}ú÷Å  +Âî¸ã ÏIõÒK/ÙMPÁ   î9>æÏ o7ÉÉ=áìF md  <-^¼8ÐÓ $ Ý Êï+yõéK­¿Á  Ø #¢ X ¶`Á Ï  ~ p{å W<ÇÇí·ßn7ÉéÔSOM¯«² ß|ó Ý y°ë =õÔSv T0õ ÒÄì©ãC#ÄýÐ 'wY  VE< °\"®cÇ é ËoÝ $ æ0Ó«ãÔñ±Ï>ûØMrzöÙg=Áàþûï·   îÂ­ª_÷óÏ?ÛMPÁ&O ì9ÏüNOuã 7zÖ× +Ä  +âÜ3§ë5 SoÀM  RÇ  Eøéû£ à  ø C ±   ê Y·nÝô6Üe ]ì&¨p* ì HsæÌ± ä´Ã ;¤×]k­µ 'b   VÄ=ñÄ   óñÇ ·   ©| ûøPy ?¶Ùf ôº-Z´°?F5ì§ ~_Ó\"ùÔ¿1u|øí?¥ A o4µ¾ß§Ô(/ VÄé  » í1Ç c7A S Z÷ ^ jý¸ä K<ëÏ 1Ãn  t>¦¶ FeÒ  nêGëî?uÚi§ÙMrzúé§=çç½÷Þk7A  °b gÏ é Ìïp|$ ^ j*¥Ôñá· ¡ê5¹/à7Üp Ý 9è©_jÛm¾ùæöÇ¨pvÿ© _~Ùn ÓQG  ^W36|÷Ýwv D  +   ÇG²í»ï¾écC ¬ýÔKS     ¤×ßm·Ýì&ÈBS ¸ÏË #FØMPávÜqÇôñ¡óì÷ß · ää ð½zõ²?FÄ °b èp|$Û wÞé9>üÖKS¨rß èD  óÏ?ß³Ý5 / b÷ Ú{ï½í&9ÙÓ¥ àã    A ã#Ùìziç s Ý$'½ t¯Ï4/ùÙl³ÍÒÛ¬mÛ¶öÇ¨pãÆ ó W£G ¶ äd÷ T KÄ  +&ÜCu 7nìk8> ¯C  éãC}öü°'*Ö  ¹}þùç ÎË' x¢Ý  Nó ¦  BúO¹§ÂjÙ²¥ý1b    W_}µç&øþûïÛMPÁÜ£Ù4êtÙ²ev  t O­¿õÖ[Û Ãbw^ 4i Ý  Î}N),ù¡0¦P Z_ Ý ? ¬ øøã = ôË/¿Ün  öä Oz     ÚMrR ÑÔº5kÖ¤ y54Z3µ½TüÑoçe$ ]>ÅïSa cp¯¯r    V 4iÒ$}Â  0Àþ  L Ó5 P7{U öKÓä¸/è*  ªýøã   øÚî  :¤»Ï'¿  ÕÏ6µ®:Êó '  X1²ÿþû{Nº_~ùÅn  D 2Ý} 4 4ªöè£ zn cÆ ± äå  ^0' |²3W  E% RÇ ß  ô4Ô]:E¥  O ¬ ¹ûî»= ö  'ÚM  ¹§ôØtÓMí ñÿ xà éíT£F óÃ ?ØMª¥ùèV[mµôw  ?Þn  Z¼xq þS*Fê¾Î«¿ â   #êWã~ÊPÈ«   =MI [ öeÑ¢Ev  §W±  5Jo§~ýúÙMòòöÛo{n¢k¬± ïúe ¦ûî»Ï³oýö Òt:©uu½W  Ä  +f:wî >ùºuëf   ì©§ òÜ (l iÂ   m4räH»IÞÜýl´Ô©SÇ¼ùæ v3Ä {¿*8ûí?¥ ¡SëwíÚÕþ 1BÀ  ã ?>}òé1ô÷ß o7 |Óô:î qZ¶Ýv[»YÅÛj«­<ÛhìØ±v ¼© ð A <ß§¾7 ` /=áÔ@ ÔþTýB?æÎ ë9 xK o ¬ ± 2<òÈ#v À  +÷ iZôjâ±Ç ³ V<{;éf ¤Âö  +Lß¾}=ß©BÂÓ§O· \" ìiÍüN >jÔ(Ïú   7 VÌ¨ dªs¬ # <Òn ä­ªp¥× ª« LêÐ¾þúë{¶×:ë¬cfÌ a7ÍÛòåË=U»µ4kÖÌÌ =Ûn  ;ýôÓÓûP?RæÏ o7ÉiàÀ éõÕ× yAã   Cî!ÀÌ  BU ®TþCå   ê`õèÑÃ³ÝÖ[o=çõN¡ .]ê ÛPK«V­ )y  ]ºtIï¿ 7ÜÐþ8' mw}µýöÛÏn  !`ÅÐyç ç¹ kÈ7à á* %K  M6ÙÄ³ýÚ´iã» ¾ ¦Gqß µh IÕ(CôÙý§   f7ÉIýùÜë?ðÀ v Ä  + ^{í5Ï xË-·ØM ¬ WáP  ÷¨^- :u2ß~û­Ý4o_}õ i×® ç;5 Lµ  mvÿ)]§ý8â #Òëj>Q 0Å  + Té·nÝºé qÏ=÷´  U\"\\ KO¬ôäÊ½=7Þxcç W¡ÔoÇ=Q° îÝ»û À ¥å  Û°aCßý§ 7o ^_£U   ¬ Úi§ Ò'£:Cþùç v À pU óæÍsú`¹·«úh©¯V¡fÎ é {TKïÞ½ÍO?ýd7E h¿   R¥9Üûú²Ë.³    X1uÝu×yNÈwß}×n ¤ ® ë³Ï>sF º·o >}|  tûøã =Uãµôïß 9H#H£nÝûI §ûqá  zÖ 6m Ý 1DÀ ©O>ùÄsB^zé¥v ÀA¸* ÕÃÒ«!÷vÖk#mÿBé Sýúõ=ß¹óÎ;;Ý   C  Iï BúO¹G¥jô(    cîwö*V Ø W¥õÎ;ï zõêy¶÷n»íæ»? Ûë¯¿njÕªåùNMÇB· èp_ õ*×   ÐÜ ©õ =öX» b   c  tPú¤TqHU  R Wåñê«¯f \"Õ4   ^|ñEç w ça  fþúë/»)JÌî?å÷mÂÝwßíY üøñv Ä  +Æî½÷^Ï ùüóÏÛMP¡ WåõÜsÏy:=kÑk¤  ~úiS£F ÏwjnR ×E ]äÙ'ê;ç F §ÖU0§ ]r °bL5s4 CêäÔ4  á* ÔñÙ=­   N:ÉnæËC =äLòîþN& .¯ ={¦÷ ¦QòC ~»_)«      s  !urjª T6ÂU´¨ ·     n7óå®»îòôÙÑrÉ% ØÍP ê?åÞ¿Ç s Ý$§  'zö#E£    s' xbúäÔEWÕ¥Q  WÑtûí·{ 4k Zçè  nð|   nAiÝsÏ= }à·ÿÔÉ'  ^WÇÈ _|a7A  °bî g ñ àcÆ ±    ®¢mäÈ   èúë¯· ù¢ æþ>Ý  æP:{íµWzû«ÿ ß F ;vL¯¯¹- , ¬ Ó ìî ¯  ~¸Ý  G¸   #Fxö  8ëu_ ç {®ç;õº I KCµÈÜ5Ê4»  ³fÍòì»³Ï>Ûn  #`% æ­J ¤ ©«, «x9ë¬³2  :® á~Í¤E .Ç  k7CÈ&M äÙî7ß|³Ý$§k¯½Ö³þ o¾i7AÌ ° À fA¿  | «xRi ÷>ÓHÃ§ zÊnæ » ¸  5k:¥\"P<§ rJz{ Ò ª_¿~éõ 7n ¨N ¢    o¼ñ çâzÓM7ÙM 0 «øRqP  uï;ÕÌ ²ïtsÞ ÿý3   =EqtêÔ)½­7Þxcûã 4 ¸Bpjý  <Ðn   `% ¦áp÷ øÏ þc7A  ®âO hðàÁ }¨NÒ 'O¶ æM×  ûîï¬[·®yûí·í¦ höìÙ íì·ÿÔc =æY?èkbD  +!vÝu×ôÉºÖZkñ¸9¡ WÉ¡NÒ»ì² g_ªè¤&x.   í·ßÞó   Z Q#<vÿ)½EðãÐC M¯«WÄK .µ    X ¡!ßî  _­ÉC¸J M 2`À Ï>mÔ¨ ïéVÜ~þùg³õÖ[{¾s uÖ13fÌ° ¢@ýû÷OoÛµ×^Û× Z½\"nÒ¤Iz}í+$  +!tñt_P Tv ? ¾3||êÔ©öG( ÂUr) õîÝÛ³ou  9s¦Ý4oêãÓ£G Ïw®·ÞzfîÜ¹vSød÷ :à  ì&9M 2Å³_®¼òJ»      -Z´H ´Ûl³ ýq^¾þúkO .ýòÝwß}Í wÞi ,X`7G  ® oÙ²e¦{÷î }Ü²eKçÇN¡ ,Yâ ¯t g 6mÌÂ  í¦ðáñÇ ÷lS¿Å Ï;ï<Ïú ~ú©Ý  AÀJ C 9$}Òê ÖO?ýd7©Ö /¾è9ùí¥C  æè£ v:iþðÃ öê  áªr|ÿý÷f£ 6òìëvíÚ9 º Jså¹G»iÑÿ_ÿ; ã  ZHÿ©nÝº¥×oÛ¶­ý1     ªàì¾ N 0ÁnR-õ ÙrË-=ß mQ D½ PñD ÝÓº  áªò|óÍ7Î  ÷>ß`  Ìwß}g7Í  XéÉ û;õdKO¸à úO5mÚ4½ ýö Ò  ÷DÝª  ä\"`% .ÎîIeU ¯P utÅ W í¶ÛÎ >î¾8g[tóW ]Í öþûïÛ_   W ëóÏ?wfdpïûÍ6ÛÌ÷  7õ½R ,÷wêÇ ú ! zeëÞ ºFúqÇ wxÖ§ l² ° F ïR'¯ßâwÙèfÿòË/ sÎ9Çl¾ùæÎT î D¶E£¡öÞ{osë­·Ò¹Ö Â æÌ c 5kæ9 ¶Øb gîÑBi  úTº¿³O >N'{äGµÆôÚVÛN%5üögs×)«S§ s®#¹ X cOß §ZaS \\Íuvì±ÇfôïÈµ¨¿ ¦ôxøá  þ&ÈD¸BÊôéÓ )TÜÇÂ¶ÛnkV¬Xa7Í  L«. û;õ   }þôjuôèÑ¾ ýh «ðkj»ï¶Ûnv $  +aÔïÊ}ñT¿¬bÓ \\*ë ©:Üõ]r-  êìyÆ g8á!ÈM#) W°}ðÁ Ná`÷1¡cä·ß~³ æí wÞñÜèµèÉ  Î x  þyÏ6¿ýöÛí&H  VÂhä »F F  Ú´iÓ JÇº è1¸û¢ mYc 5Lß¾}Í  #   : V Â ²yë­·2Î£=÷Ü3P Ò  :¾Üß© H~ fÂ  N8!½­õ 3ÈèPÄ  + T +u\"«6V9i: ×^{Í©ý¢Ñ  Öì¾¨g[ 4h`öØc gâêY³fÙ_ ( +Tç¥ ^Ê D  tP  \"ê`­I¦Ýß©Wø( Tß--ÿú×¿ì  @ ¬ R w÷E3JSdhÔÒ¸qã _s]ºtñü ¹ õ×_ß©?óà   E  Ù_ [ +äküøñ §ÓZT . õ¥´ ô |òÉv3 dÏ´qî¹çÚM @ ¬ Ò< î Yó F   «Ã¨~ 7oÞÜówçZTÇçÔSO5Ï>ûlA U£ p ¿Tà× Å{Úi§ÙÍ|Q?MÕ´s çðáÃíf àª«®òl_æ ­  ¬ R? wÇØ^½zÙM\"KÓF  5Êì²Ë.  q³-úU¯   ]t yóÍ7cÑ  p BÝ{ï½ b Z.¸à » /êpí®¡§Eõì   þLm×u×]7Ð«]Ä  +¡ì)7 ¸Ô1öæ o6³gÏ¶ G :ñ¾ñÆ æÂ /têõØ¯G²-ªO³ë®»:Oî¢ôz4 p  n¹å  @¤§$A\\wÝu çÒ 7Ü`7 O R¬F  émZ  G(  V =òÈ# ¿píE ¢ ?üp§OS i8JI¯ U Bµ¾ì  kQGÿ  >ØÜwß}ÎT åD¸BX®¾újÏq¤À¥ PAØý7u Q   NuÿÜÛôÑG µ  ¡ X £pe÷ÑÈgÙtÓM ¾ êÓ  ÊÎ °öþûï7  z¨iÙ²eÆ¿)Û²á    N:É<óÌ3 *cûE¸BØôt×}<) ©Oc  [Ôý ê õÐC ÙÍ 'õ/MmK=ÉR¡fT  V T ®vÞyg3räHçÿõÛ§éâ /vjðÄ¡O Ì 9ÓÜxã f÷Ýw7õë×ÏøwUµè ×»wosþùç É ';e%  p b9ýôÓ=Ç ® A  h b÷wj¤áÓO?m7C5títOO¤Z ¨  ¬ ¨*\\ pÀ  p¤>M¯¿þºÓ!v«­¶òô Èµ¨O ¦uP  Ï>ûÌõ_ .u\"ÕH ½òP S»ÞO¶E PUè4L + Û1Ç ã9¾tnë m¡tþ¨, û;u ½øâ vSd¡¾®g y¦g ^sÍ5v3$  + ò WUÑë1ÕÖQÝ ®]»zÖÏµ¨O :jªOS1æ:, ½öÔT úµ¯\" ö¿É½¨¢uX W(  \"õ3t g  aÒ¤IvÓ¼éú1xð`ÏwÖªUËyÒ L ~ø¡3 z¯½öÊ ¨;µ<õÔSöjH0 VÌ  ®ª¢°¤>M O~û4)¤ ºOS   /vú  qÄ ¦uëÖ  Ï W^i7/ á ¥¤'Ôº¹» 7M±£Ò% Ò+su/p §F$'©Øo!tnëmÀ¥ ^ê ãùvIhÔ¨  ÄP  X1 f¸ª ^ ªO & Í÷ ¢W zý¨× ú¥ d¾´R 3g óDîÉ' ´?* á å I    ä9î4íÔûï¿o7ÍÛ/¿üb   àùÎ  'ÚÍ M3Ph Ð9ç ã  ÑÓAûÚ m±Ëi ²*  +¦  ®lz ¡ ïêÓ¤¹ óíÓ¤ õ;í´ ÓÑ~úôéö×& á å´bÅ §3µûø[{íµ   * 2pà@ç»zôèáü7 LOè  üqgJ/u)°+ÝçZÔgUçÿ ÿû_çG¦F;wïÞÝÓ  U  X1TêpU õiÒd±*í°Ùf e\\d²-ê pà   {î¹Ç|ùå ö×Æ á Q @´Å [d {A   » \\±Ì ;×¹&©Ë@§N 2®[¹ ¦M :¯fõ#ò  >°¿Ú±téRBV \"`ÅL ÂUUT¬tÌ 1NñR»OS®¥sçÎfèÐ¡NçO=  3Â ¢D7u{@    þùçvÓ óÑG 9£¢÷Ùg ³Þzëe\\ r-íÛ·wjï© ë¬Y³ì¯Î  Uy X1 ÕpU ýRÖt   §aÃ   ©ª ýÛô«[3Í¿òÊ+N  ¸ \\! ôÃ§K . ã²C  ± ý  ]GÔÑÿòË/wº+¨O }íÉ¶¨p«& W]0]  n7BVe!`ÅD ÂUU¦L â ¸é×¯_Þ Dk×®mvØa gJ  ?þØþÊÈ \\!Ê¾úê+Ó®];Ïñ©², I D É¬ ,ú¡¦þ¢: íkK¶E}KUxXõ«TÂ¦ U× Y     q W6u UÁÂaÃ 9  êæML-* ºß~û ;ï¼32¯9 W  ùóçg ^éÖ­[Q D©)(>ñÄ ÎôWº Ø×Ê\\  ál¿ýöfÄ  æÕW_uFL  !«2 °\".iáª*K ,qþ G ydÆ/í\\ ^u¨ µFûhÆúR#\\!NÔ_¨I & ãUOkÔ!>N  ï½÷^3dÈ ³Á  d\\ r-ú ¶Ç { k¯½Ö)]¡ÑÑåBÈJ> V UB¸ªÊ¼yóÌí·ßît@mÜ¸qÆE²ªEÃ¨5|üì³ÏvªW+ü  á q4mÚ4ç&î>nû÷ï_²'7 P÷  nºÉì»ï¾Î, ö¹ kÑ 6U¸¿ã ;\"9Í !+Ù X U©áª* ú|Å W8ux4U } ­jQ;µWÇÖlC§ E¸B ½ûî»   U­½X  û¡¿Aõö4 Â.»ìâT ·Ïíl  zn¼ñÆÎ¨dÍÒ ¾gq@ÈJ. V  ®²S¸yé¥  'U={öÌ»   -ê Øm·Ýæ<!+ á I i^4 Ä} ëü(õ5F¯'Õ ó¼óÎs £æû JKÍ 5M¯^½Ì g aÆ  W n a!d%  +b Wþè¢ª ®ê Õ±cÇ  p¶¥mÛ¶N ¯  ~Øé   Â  DÁÆ Ñ«úNÅì ¤smìØ±æ SNq^é¯¶Új çf¶Eó*ê©ôÅ _l^~ùåÄU 'd%  +B WÁit¡  j´¡Ý¡7Û¢W  Q¥Q ºéTuá&\\! ôäGó º k½b  ÎGM  ÔQG9 ÂÛç^®Eý/5 ê5×\\cÞ{ï½ ¸  ²      V «5 W¡S Y] UOË~% mÑ¯zÕëÒ\\bªßE¸B é)®ýª]u  ñÉ' 8  ÷ß  ³þúëg [¹ 6mÚ8Óhé5þ  3ì¯®  ¬ä `EÀü áê  ájó 'R ÿ B ®Â§ Îªu£þ ê» ïë » á I£§¾v=:Mì Ë  üaÞyç § ð®»î 1:±ºe£ 62Ç {¬yðÁ ÍÂ  í¯¯h ¬d ` ÛÊp5}e¸ºgå 4tå¢ u áª$4÷áÓO?íL  o= Â  JsóÙÇûu×] þ\\ ¼« Ê  \\à vÈ÷ ° ½ Ü|óÍÍé§ îÌ;ª  Ü YñGÀ*§ áÊ¬ W+Ï óÉÊE!k4áªl¾üòK3zôhç5EóæÍ3n ú O¸B ©¬ û WÿDu,× ]»¯V®EáK!ìÂ /tB Â ü#dÅ  «\\\\á*µ,\\ ®þ\"\\EÆôéÓÍUW]å 7Ô Õ*v $ æð³ Su núzM¨× ê·¨×   !+¾ XåPE¸2+Ã !\\    O>9#D¹ V­Z9 ÙÕ¡]?DP\\ ¬x\"`  á @ ¨NUêf®)g >úh§äBT&Z¯4 ¬ø!`  á @ èæ½`Á û F T ²z¯ Y  Y DÀ* Â    wÈêºr9`årÓÊ õ#!+r X¥@¸   D!kðÊ ¥puõÊeÒÊå  !Ë ²\"  Ul + @È~^ ² X ² ® ¦î- ¬H!`  á  P$¿¯ Y?® Y { !+2 XÅB¸    ªâ ²\"  U  + @© ²\"   6Â   Ô Y CÀ  á  P. ¬H!`  p  (7BVd °Â@¸  D !+  XA ®  QCÈ*; V  + @T ²Ê  U¨G %\\  ¢  U6 ¬B,_nLíÚ + @ô ²Ê  U  ¬5j ®  ñ@È*9 V¡Æ 1¦O c  '\\  ¢  UR ,  * !«d X  T BVI °  ¨4 ¬¢#`  P  YEUþ åÞ±~ Ywútcþû_c¶ÙÆ V­ ©Uë  þï­·6fÄ c>ùÄ^   äÈ ²fÎ´[Â§  IÈ  ¤BÖýüscößß  üÃ»~U Úì»¯1  Øß  @2(duëæ½ÿ {¬Ý >ùH&ERHHJñ»î[o Ó¸qf ªnY{íÿ­  @ -Yâ Y#GÚ-àS É¤ ÜAÆ/?ëN bÌ kz×éÛ× ;ï4fÖ,c~üÑ  ~2fölcî¾Û   ¼mµ®¾   $Ò=ðºë ¹÷^cþúËþ >å L ÌOH²å»î ? Ó¦ÍßmëÕ3fÜ8»U¦ñã ©_ÿïõô ú.    ªI&% oHªJ¾ë qÆßí4     j«Îï©õ  ³[  Py 1X,ßûzU ¬[ åÿ+ l |Ö]¶Ìû ê¶Ûì Õ»å ¿××wéu\"   ¨  Åò¹¯g dÝ\"(ÿ_ d ä³î}÷ýÝ¦uëÂæ Ô:Jä©ïÑw  Pi =XÌ½ _AÖ- òÿ A6H>ë qÄßm.¸Àþ4 ç ÿ÷÷  b   @² b°X>÷õl ¬[ åÿ+ l |ÖÝd ¿ÛL d  ¿ _üû{ô   T R  Ëç¾ M u  ü  {  Y²iÚôï6_~i  ¿  ÿþ fÍìO  H®R  Ëç¾ M u  ü  {  Y²Y}õ¿Û¬Xa  ¿  þû{ÖXÃþ   d*å`±|îëÙ Y· ÊÿW¸7H % °  ÖM}    P)J9X, ûz6AÖ- òÿ A6H>ëò    Â r°X>÷õl ¬[ åÿ+ l |Ö¥ ;   +å}4 ûz6AÖ- òÿ A6H>ë 2y   4¥| ä¾¯ Y\" ü E   ÏºîwÇ  Zè»c½wN}O¶wÇ  $MX} ó ,æ¾¯ Y\" ü E   Ïº ý z ©v·ßn·¨ FL¤Ö×è }'    ¬  Ï`1÷}=È  åÿ+ l |×=í´¿ÛÕ©cÌÔ©v ìÔV5?Rë«     ¢\\¯ ý ²n  ÿ¯ ²Aò]wéRïðÐ    0Án éÙgÿ×6µ ^ ê»  ¨ tr/Hùÿ   ÄÏºo¿ý¿G îuú÷ÿß|I 7iùòÿÍ¡4g 1£G 3p ·­  ê;  ¨$¥ ,æç¾n ²n  ÿ¯ ²Aü®ûúëÆ4jä]/ Eëh]  *M)  ¹ï½~ Y· ÊÿW Ù  ¬; ¾1   ó  x×¯jQ ½÷6fÞ<û[  ¨ ¥ ,VÈ}=%ÈºEPþ¿\"È  ²î'  sÉ%ÆôécÌúë ³æ ÿ[Z¶üßÿvñÅÆL f¯  @å)Õ`± ÷õ ë A4þ    ]¥ , $$ Y· ¢ñW   h+Å`± !)ÈºE  ¿   D_±    IAÖ- hü      9X,HH ²n Dã¯   ñR ÁbABR u       @  °   BFÀ          d ,     °   BFÀ          d ,     °   BFÀ          d ,     °   BFÀ          d ,     °   BFÀ          d ,     °   BFÀ          d ,     °   BFÀ          d ,     °   BFÀ          d ,     °   BFÀ          d ,     °   BFÀ          d ,     °   BFÀ          d ,     °   BFÀ          d ,     °   BFÀ          d ,     °   BFÀ          d ,     °   BFÀ          d ,     °   BFÀ          d ,     °   BFÀ          d ,     °   BFÀ          d ,     °   BFÀ          d ,     °   BFÀ          d ,     °   BFÀ          d ,     °   BFÀ          d ,     °   BFÀ          d ,     °   BFÀ          d ,     °   BFÀ          d ,     °   BFÀ          d ,     °   BFÀ          d ,     °   BFÀ          d ,     °   BFÀ          d ,     °   BFÀ          d ,     °   BFÀ          d ,    ý?Êqu   ðf    IEND®B` ".getBytes())
				.build();// Example byte array for PNG image
			
	}
	
    @Test
	void testImageByGsidPng() throws Exception {

	mockMvc.perform(get("/chemical/file/image/search/by-gsid/{gsid}", "20182")
			.param("format", "PNG"))
  			.andDo(MockMvcResultHandlers.print())
  			.andExpect(status().isOk())
  			.andExpect(content().contentType("image/png"));
	
    }
    
    @Test
	void testImageByGsidSvg() throws Exception {
    	
    when(detailRepository.getMolFileForGsid("20182")).thenReturn(Optional.of(fileDetails.getMolFile()));
    try (MockedStatic<ChemicalImageUtils> mockedStatic = Mockito.mockStatic(ChemicalImageUtils.class)) {
            mockedStatic.when(() -> ChemicalImageUtils.smileToImage(any(String.class), eq(ImageFormat.SVG))).thenReturn("<svg></svg>".getBytes());
    
	mockMvc.perform(get("/chemical/file/image/search/by-gsid/{gsid}", "20182")
			.param("format", "SVG"))
  			.andDo(MockMvcResultHandlers.print())
  			.andExpect(status().isOk())
  			.andExpect(content().contentType("image/svg+xml"));
	
      }
    }
    
    @Test
	void testImageByDtxsidPng() throws Exception {

	mockMvc.perform(get("/chemical/file/image/search/by-dtxsid/{dtxsid}", "DTXSID7020182")
			.param("format", "PNG"))
  			.andDo(MockMvcResultHandlers.print())
  			.andExpect(status().isOk())
  			.andExpect(content().contentType("image/png"));
	
    }
    
    @Test
	void testImageByDtxsidSvg() throws Exception {

    when(detailRepository.getMolFileForDtxsid("DTXSID7020182")).thenReturn(Optional.of(fileDetails.getMolFile()));
    try (MockedStatic<ChemicalImageUtils> mockedStatic = Mockito.mockStatic(ChemicalImageUtils.class)) {
            mockedStatic.when(() -> ChemicalImageUtils.smileToImage(any(String.class), eq(ImageFormat.SVG))).thenReturn("<svg></svg>".getBytes());
        
	mockMvc.perform(get("/chemical/file/image/search/by-dtxsid/{dtxsid}", "DTXSID7020182")
			.param("format", "SVG"))
  			.andDo(MockMvcResultHandlers.print())
  			.andExpect(status().isOk())
  			.andExpect(content().contentType("image/svg+xml"));
      }
    }
    
    @Test
	void testImageByDtxcidPng() throws Exception {

	mockMvc.perform(get("/chemical/file/image/search/by-dtxcid/{dtxcid}", "DTXCID30182")
			.param("format", "PNG"))
  			.andDo(MockMvcResultHandlers.print())
  			.andExpect(status().isOk())
  			.andExpect(content().contentType("image/png"));
	
    }
    
    @Test
	void testImageByDtxcidSvg() throws Exception {

    when(detailRepository.getMolFileForDtxcid("DTXCID30182")).thenReturn(Optional.of(fileDetails.getMolFile()));
    try (MockedStatic<ChemicalImageUtils> mockedStatic = Mockito.mockStatic(ChemicalImageUtils.class)) {
            mockedStatic.when(() -> ChemicalImageUtils.smileToImage(any(String.class), eq(ImageFormat.SVG))).thenReturn("<svg></svg>".getBytes());
          
	mockMvc.perform(get("/chemical/file/image/search/by-dtxcid/{dtxcid}", "DTXCID30182")
			.param("format", "SVG"))
  			.andDo(MockMvcResultHandlers.print())
  			.andExpect(status().isOk())
  			.andExpect(content().contentType("image/svg+xml"));
      }
    }
               
    @Test
	void testMolByDtxsid() throws Exception {
    	final Optional<String> mol = Optional.of(fileDetails.getMolFile());
    	
    	when(detailRepository.getMolFileForDtxsid("DTXSID7020182")).thenReturn(mol);
    	
	mockMvc.perform(get("/chemical/file/mol/search/by-dtxsid/{dtxsid}", "DTXSID7020182"))
  			.andDo(MockMvcResultHandlers.print())
  			.andExpect(status().isOk())
  			.andExpect(content().contentType(TEXT_PLAIN));
	
    }
    
    @Test
	void testMolByDtxcid() throws Exception {
    	final Optional<String> mol = Optional.of(fileDetails.getMolFile());
    	
    	when(detailRepository.getMolFileForDtxcid("DTXCID30182")).thenReturn(mol);
    	
	mockMvc.perform(get("/chemical/file/mol/search/by-dtxcid/{dtxcid}", "DTXCID30182"))
  			.andDo(MockMvcResultHandlers.print())
  			.andExpect(status().isOk())
  			.andExpect(content().contentType(TEXT_PLAIN));
	
    }
    
    @Test
	void testMrvByDtxsid() throws Exception {
    	final Optional<String> mol = Optional.of(fileDetails.getMolFile());

    	
    	when(detailRepository.getMrvFileForDtxsid("DTXSID7020182")).thenReturn(mol);


	mockMvc.perform(get("/chemical/file/mrv/search/by-dtxsid/{dtxsid}", "DTXSID7020182"))
  			.andDo(MockMvcResultHandlers.print())
  			.andExpect(status().isOk());
	
    }
      
    @Test
	void testMrvByDtxcid() throws Exception {
    	
final Optional<String> mrv = Optional.of(fileDetails.getMrvFile());

    	
    	when(detailRepository.getMrvFileForDtxcid("DTXCID30182")).thenReturn(mrv);


	mockMvc.perform(get("/chemical/file/mrv/search/by-dtxcid/{dtxcid}", "DTXCID30182"))
  			.andDo(MockMvcResultHandlers.print())
  			.andExpect(status().isOk())
  			.andExpect(content().string(fileDetails.getMrvFile()));
	
    }
    
    @Test
    void testGenerateImageBySmilesPng() throws Exception {
		final String smiles = "CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1";

		mockMvc.perform(get("/chemical/file/image/generate")
				.param("smiles", smiles)
				.param("format", "PNG"))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("image/png"));
	}
    
    @Test
    void testGenerateImageBySmilesSvg() throws Exception {
		final String smiles = "CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1";

		mockMvc.perform(get("/chemical/file/image/generate")
				.param("smiles", smiles)
				.param("format", "SVG"))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("image/svg+xml"));
	}
}
