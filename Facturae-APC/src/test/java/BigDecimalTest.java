import org.apache.log4j.Logger;

import es.mityc.appfacturae.utils.io.DoubleUtil;

public class BigDecimalTest {
	public static Logger logger = Logger.getLogger(BigDecimalTest.class);
	public void testScale(Double bd) {
		Double result = DoubleUtil.round(bd, 2);
		logger.info(String.format("\tOrigen %s Resultado %s", bd, result));
		logger.info(String.format("\tOrigen %s Resultado %s", bd, DoubleUtil.redondeo(result)));
	}
	
	public static void main(String []args) {
		BigDecimalTest bdt = new BigDecimalTest();
		bdt.testScale(0.031);
		bdt.testScale(0.0031);
		bdt.testScale(0.131);
		bdt.testScale(0.11);
		bdt.testScale(0.119);
		bdt.testScale(121.031);
		bdt.testScale(0.029);
	}
}
