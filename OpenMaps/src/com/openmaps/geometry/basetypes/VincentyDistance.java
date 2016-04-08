package com.openmaps.geometry.basetypes;

public class VincentyDistance {
	public static double vincentyConstantA = 6378137;
	public static double vincentyConstantB = 6356752.3142;
	public static double vincentyConstantF = 1/298.257223563;

	public static double degtoRad(double val){
		return val*Math.PI/180;
	}
	
	public static double calculateDistanceBetweenTwoPoints(Location p1, Location p2){
		double a = vincentyConstantA;
		double b = vincentyConstantB; 
		double f = vincentyConstantF;

		double L = degtoRad(p2.x - p1.x);
		double U1 = Math.atan((1-f) * Math.tan(degtoRad(p1.y)));
		double U2 = Math.atan((1-f) * Math.tan(degtoRad(p2.y)));
		double sinU1 = Math.sin(U1);
		double cosU1 = Math.cos(U1);
		double sinU2 = Math.sin(U2);
		double cosU2 = Math.cos(U2);
		double lambda = L;
		double lambdaP = 2*Math.PI;
		double iterLimit = 20;
		
		double cosSqAlpha=0.0 ;
		double cos2SigmaM=0.0;
		double sigma=0.0;
		double sinSigma=0.0;
		double cosSigma=0.0;
		while (Math.abs(lambda-lambdaP) > 1e-12 && --iterLimit>0) {
			double sinLambda = Math.sin(lambda);
			double cosLambda = Math.cos(lambda);
			 sinSigma = Math.sqrt((cosU2*sinLambda) * (cosU2*sinLambda) +
					(cosU1*sinU2-sinU1*cosU2*cosLambda) * (cosU1*sinU2-sinU1*cosU2*cosLambda));
			if (sinSigma==0) {
				return 0;  // co-incident points
			}
			 cosSigma = sinU1*sinU2 + cosU1*cosU2*cosLambda;
			sigma = Math.atan2(sinSigma, cosSigma);
			double alpha = Math.asin(cosU1 * cosU2 * sinLambda / sinSigma);
			cosSqAlpha = Math.cos(alpha) * Math.cos(alpha);
			cos2SigmaM = cosSigma - 2*sinU1*sinU2/cosSqAlpha;
			double C = f/16*cosSqAlpha*(4+f*(4-3*cosSqAlpha));
			lambdaP = lambda;
			lambda = L + (1-C) * f * Math.sin(alpha) *
					(sigma + C*sinSigma*(cos2SigmaM+C*cosSigma*(-1+2*cos2SigmaM*cos2SigmaM)));
		}
		if (iterLimit==0) {
			return 0.0;  // formula failed to converge
		}
		double uSq = cosSqAlpha * (a*a - b*b) / (b*b);
		double A = 1 + uSq/16384*(4096+uSq*(-768+uSq*(320-175*uSq)));
		double B = uSq/1024 * (256+uSq*(-128+uSq*(74-47*uSq)));
		double deltaSigma = B*sinSigma*(cos2SigmaM+B/4*(cosSigma*(-1+2*cos2SigmaM*cos2SigmaM)-
				B/6*cos2SigmaM*(-3+4*sinSigma*sinSigma)*(-3+4*cos2SigmaM*cos2SigmaM)));
		double s = b*A*(sigma-deltaSigma);
		return s;
	}


}
