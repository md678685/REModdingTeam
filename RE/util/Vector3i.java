/**
 * 
 */
package RE.util;

import java.io.Serializable;
import java.nio.FloatBuffer;

import org.lwjgl.util.vector.Vector;
import org.lwjgl.util.vector.Vector3f;

/**
 * @author Larethian
 * 
 */
public class Vector3i implements Serializable {

	public int x, y, z;

	public Vector3i() {
		super();
	}
	
	public Vector3i(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vector3i(Vector3i v) {
		this.x = v.x;
		this.y = v.y;
		this.z = v.z;
	}
	
	public Vector3i set(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}
	
	public Vector3i set(Vector3i v) {
		this.x = v.x;
		this.y = v.y;
		this.z = v.z;
		return this;
	}

	public float lengthSquared() {
		return this.x * this.x + this.y * this.y + this.z * this.z;
	}

	/**
	 * Translate a vector
	 * @param x The translation in x
	 * @param y the translation in y
	 * @param z The translation in z
	 * @return this
	 */
	@SuppressWarnings("hiding")
	public Vector3i translate(float x, float y, float z) {
		this.x += x;
		this.y += y;
		this.z += z;
		return this;
	}
	
	/**
	 * Add a vector to another vector and place the result in a destination
	 * vector.
	 * 
	 * @param left
	 *            The LHS vector
	 * @param right
	 *            The RHS vector
	 * @param dest
	 *            The destination vector, or null if a new vector is to be
	 *            created
	 * @return the sum of left and right in dest
	 */
	public static Vector3i add(Vector3i left, Vector3i right, Vector3i dest) {
		if (dest == null)
			return new Vector3i(left.x + right.x, left.y + right.y, left.z
					+ right.z);
		dest.set(left.x + right.x, left.y + right.y, left.z + right.z);
		return dest;
	}
	
	public Vector3i add(Vector3i right){
		return add(this, right, this);
	}
	
	/**
	 * Subtract a vector from another vector and place the result in a destination
	 * vector.
	 * @param left The LHS vector
	 * @param right The RHS vector
	 * @param dest The destination vector, or null if a new vector is to be created
	 * @return left minus right in dest
	 */
	public static Vector3i sub(Vector3i left, Vector3i right, Vector3i dest) {
		if (dest == null)
			return new Vector3i(left.x - right.x, left.y - right.y, left.z - right.z);
		dest.set(left.x - right.x, left.y - right.y, left.z - right.z);
		return dest;
	}
	
	public Vector3i sub(Vector3i right){
		return sub(this, right, this);
	}
	
	/**
	 * The cross product of two vectors.
	 *
	 * @param left The LHS vector
	 * @param right The RHS vector
	 * @param dest The destination result, or null if a new vector is to be created
	 * @return left cross right
	 */
	public static Vector3i cross(
			Vector3i left,
			Vector3i right,
			Vector3i dest)
	{

		if (dest == null)
			dest = new Vector3i();

		dest.set(
				left.y * right.z - left.z * right.y,
				right.x * left.z - right.z * left.x,
				left.x * right.y - left.y * right.x
				);

		return dest;
	}
	
	public Vector3i cross(Vector3i right){
		return cross(this,right,this);
	}
	
	/**
	 * Negate a vector
	 * @return this
	 */
	public Vector3i negate() {
		this.x = -this.x;
		this.y = -this.y;
		this.z = -this.z;
		return this;
	}
	
	/**
	 * Negate a vector and place the result in a destination vector.
	 * @param dest The destination vector or null if a new vector is to be created
	 * @return the negated vector
	 */
	public Vector3f negate(Vector3f dest) {
		if (dest == null)
			dest = new Vector3f();
		dest.x = -this.x;
		dest.y = -this.y;
		dest.z = -this.z;
		return dest;
	}

	/**
	 * The dot product of two vectors is calculated as
	 * v1.x * v2.x + v1.y * v2.y + v1.z * v2.z
	 * @param left The LHS vector
	 * @param right The RHS vector
	 * @return left dot right
	 */
	public static int dot(Vector3i left, Vector3i right) {
		return left.x * right.x + left.y * right.y + left.z * right.z;
	}
	
	public int dot(Vector3i right){
		return dot(this,right);
	}
	
	/**
	 * Calculate the angle between two vectors, in radians
	 * @param a A vector
	 * @param b The other vector
	 * @return the angle between the two vectors, in radians
	 */
	public static float angle(Vector3i a, Vector3i b) {
		float dls = dot(a, b) / (a.length() * b.length());
		if (dls < -1f)
			dls = -1f;
		else if (dls > 1.0f)
			dls = 1.0f;
		return (float)Math.acos(dls);
	}
	
	public float angle(Vector3i b){
		return angle(this,b);
	}
	
	public Vector3i scale(int scale) {
		this.x *= scale;
		this.y *= scale;
		this.z *= scale;
		return this;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder(64);

		sb.append("Vector3i[");
		sb.append(this.x);
		sb.append(", ");
		sb.append(this.y);
		sb.append(", ");
		sb.append(this.z);
		sb.append(']');
		return sb.toString();
	}

	/**
	 * The dot product of two vectors is calculated as
	 * v1.x * v2.x + v1.y * v2.y + v1.z * v2.z
	 * @param left The LHS vector
	 * @param right The RHS vector
	 * @return left dot right
	 */
	public static float dot(Vector3f left, Vector3f right) {
		return left.x * right.x + left.y * right.y + left.z * right.z;
	}
	
	/**
	 * @return the length of the vector
	 */
	public final float length() {
		return (float) Math.sqrt(lengthSquared());
	}
	
	/**
	 * @return x
	 */
	public final int getX() {
		return this.x;
	}

	/**
	 * @return y
	 */
	public final int getY() {
		return this.y;
	}
	
	/**
	 * @return z
	 */
	public final int getZ() {
		return this.z;
	}

	/**
	 * Set X
	 * @param x
	 */
	public final void setX(int x) {
		this.x = x;
	}

	/**
	 * Set Y
	 * @param y
	 */
	public final void setY(int y) {
		this.y = y;
	}

	/**
	 * Set Z
	 * @param z
	 */
	public void setZ(int z) {
		this.z = z;
	}
}
