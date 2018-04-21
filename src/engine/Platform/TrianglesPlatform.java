package engine.Platform;

import java.util.Arrays;

public class TrianglesPlatform {
	private boolean isOriginBased;//true if both triangles touch the origin
	private float xmin = 9e37f;
	private float xmax = -9e37f;
	private float zmin = 9e37f;
	private float zmax = -9e37f;
	private Plane firstHalf;
	private Plane secondHalf;
	public TrianglesPlatform(int triangleAIndex, int triangleBIndex, float[] vertices, int[] indices) {
		int[] triangleA = new int[3];
		triangleA[0] = indices[triangleAIndex * 3];
		triangleA[1] = indices[triangleAIndex * 3 + 1];
		triangleA[2] = indices[triangleAIndex * 3 + 2];
		int[] triangleB = new int[3];
		triangleB[0] = indices[triangleBIndex * 3];
		triangleB[1] = indices[triangleBIndex * 3 + 1];
		triangleB[2] = indices[triangleBIndex * 3 + 2];
		int[] cornerIndices = new int[4];
		int l = 0;
		for (int i : triangleA) {
			if (Arrays.binarySearch(cornerIndices, i) == 0) {
				cornerIndices[l] = i;
				l++;
			}
		}
		for (int i : triangleB) {
			if (Arrays.binarySearch(cornerIndices, i) == 0) {
				cornerIndices[l] = i;
				l++;
			}
		}
		for (int i = 0; i<cornerIndices.length;++i) {
			float x = vertices[cornerIndices[i]];
			float z = vertices[cornerIndices[i]];
			if (x < xmin) {
				xmin = vertices[cornerIndices[i]];
			}else if (x > xmax) {
				xmax = x;
			}
			if (z < zmin) {
				zmin = z;
			}else if (z > zmax) {
				zmax = z;
			}
		}
		for (int i = 0; i<cornerIndices.length;++i) {
			float x = vertices[cornerIndices[i]];
			float z = vertices[cornerIndices[i]];
			if ((xmin - x)*(xmin - x) < 0.00001f && (zmin - z)*(zmin - z) < 0.00001f) {
				if (Arrays.binarySearch(triangleA, i) == 0) {
					if (Arrays.binarySearch(triangleB, i) == 0) {
						isOriginBased = true;
					}else {
						isOriginBased = false;
					}
				}else {
					isOriginBased = false;
				}
				break;
			}
			int countCorrect = 0;
			for(int k:triangleA) {
				if ((vertices[k] - xmin)*(vertices[k] - xmin) < 0.00001f) {
					++countCorrect;
				}
			}
			if (countCorrect >= 2) {
				int vertA = triangleA[0];
				int vertB = triangleA[1];
				int vertC = triangleA[2];
				firstHalf = new Plane(vertices[vertA*3], vertices[vertA*3+1], vertices[vertA*3+2], vertices[vertB*3], vertices[vertB*3+1], vertices[vertB*3+2], vertices[vertC*3], vertices[vertC*3+1], vertices[vertC*3+2]);
				vertA = triangleB[0];
				vertB = triangleB[1];
				vertC = triangleB[2];
				secondHalf = new Plane(vertices[vertA*3], vertices[vertA*3+1], vertices[vertA*3+2], vertices[vertB*3], vertices[vertB*3+1], vertices[vertB*3+2], vertices[vertC*3], vertices[vertC*3+1], vertices[vertC*3+2]);
			}else {
				int vertA = triangleB[0];
				int vertB = triangleB[1];
				int vertC = triangleB[2];
				firstHalf = new Plane(vertices[vertA*3], vertices[vertA*3+1], vertices[vertA*3+2], vertices[vertB*3], vertices[vertB*3+1], vertices[vertB*3+2], vertices[vertC*3], vertices[vertC*3+1], vertices[vertC*3+2]);
				vertA = triangleA[0];
				vertB = triangleA[1];
				vertC = triangleA[2];
				secondHalf = new Plane(vertices[vertA*3], vertices[vertA*3+1], vertices[vertA*3+2], vertices[vertB*3], vertices[vertB*3+1], vertices[vertB*3+2], vertices[vertC*3], vertices[vertC*3+1], vertices[vertC*3+2]);
				
			}
		}
		
	}
}
