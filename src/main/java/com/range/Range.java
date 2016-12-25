package com.range;

import java.util.List;

import com.annotation.Uncompleted;
import com.google.common.collect.Lists;

/**
 * null is undefined.
 * 
 * immutable class����������ȫ�����⣬��������ʵ����ȷ�����⡪�����ʿ������⡣
 * 
 * @author bl05386
 *
 *         public class Range<T extends Comparable<? super T> , Clonable>
 *         ===>ֻ��˵����Щ�������Ĳ�����֣���ģ����������ռ���Ĳ�������
 *         
 *         ����һ��ʼ������Ƴ����еĽӿڣ� ����Ҫ�Ĺ��ܲ�ͬ���ӿںͶ�������Կ��ܲ�ͬ����������CPUָ��ϵͳ�Ϳ�Դ���֮�⣡���������ڼ�����������⣬�����ڸ�����������⡣
 *         
 *         ����ʵ��һ���ӿڶ��ٶ���һ���ӿڣ� ˵����һ��ʼ����Ƴ�"����"�Ľӿڵ��Ѷȣ�������Ҫ�����Ӷ����ά�ȡ������ӶȺͼ�Ӻͽ���ĳ̶����ӡ������ǿ��Խ��ܵģ�����������
 *         ������һ��ģ�ͺ󣬽ӿ�Ӧ���ܹ������ġ�һ�µı���Ŀ��ģ�ͣ� �������Ƕ����������������գ����Ǵ��������弫�������Ǿ��޷��ӽӿ��Ϲ۲���������Ƶ�Ŀ��ģ�ͣ�
 *
 *  �����ʵ��������Щ���ӣ�Դ�ڸ����ϵĸ����ԣ���ֵ����µġ�
 *  
 *  ���п�range�ĸ�����жϣ��޷��жϡ�����Ķ���û�ж���ã�����ģ���ϳ��������⣡����
 *  
 *  һ��������ʵ�ֹ����з��ָ���Ķ��岻������Ҫ��չ��һ��ʼ�Ͷ���������ĸ������ǰ�߸��򵥵����з��գ������޷�ʵ�֣���Ҫ��һЩ�߽������û���ڸ���ģ���ж���á����ֻص�״̬�ռ�����������ˣ�������
 *  ����ģ�͵ķ�Χ���ڶ������Ĳ����Ǿ�Ӧ�ö��������ģ�������ʵ�֣������ע���ǿ�ʵ���ԣ�ʵ�ֹ�ע����ʵ�ֵĺû����⣺�������Ŀ�ά���Եȣ���
 *  
 * @param <T>
 */

@Uncompleted(description = "immutable is not satisfied.") // ʹ��ʱ��T�����ǲ��ɱ�ġ�
public class Range<T extends Comparable<? super T>> {
    public static enum OpenCloseEnum {
        doubleOpen("open range"), doubleClose("close range"), onlyLeftClose("left-close-right-open range"), onlyRightClose("left-open-right-close range");
        private OpenCloseEnum(String description) {
            this.description = description;
        }

        public String getDescripion() {
            return this.description;
        }

        private String description;
    }

    private T min;
    private T max;
    private OpenCloseEnum rangeType;

    /**
     * 
     * @param one
     *            not null .null is undefined.
     * @param two
     * @param rangeType
     */
    public Range(T one, T two, OpenCloseEnum rangeType) {
        T min = one.compareTo(two) >= 0 ? two : one;
        this.min = min;
        T max = two.compareTo(one) >= 0 ? two : one;
        this.max = max;
        this.rangeType = rangeType;
    }

    /**
     * default doubleClose type.
     * 
     * @param one
     * @param two
     */
    public Range(T one, T two) {
        this(one, two, OpenCloseEnum.doubleClose);
    }

    /**
     * ��������Ѿ�û������,�ǲ����õĽӿڡ���Ϊ�Ѿ��ṩ��extremeMin����. �߼��ȼ���hasMin+extremeMin.
     * @throws IllegalStateException
     * @return
     */
    @Deprecated
    public T min() {
        if (hasMin()) {
            return min;
        } else {
            throw new IllegalStateException("range is in a status(has no min) which does not support this operation.");
        }

    }

    /**
     * @return
     */
    public boolean hasMin() {
        List<OpenCloseEnum> hasMinEnums = Lists.newArrayList(OpenCloseEnum.doubleClose, OpenCloseEnum.onlyLeftClose);
        if (hasMinEnums.contains(this.rangeType)) {
            return true;
        }

        return false;
    }

    /**
     * @return
     */
    public boolean hasMax() {
        List<OpenCloseEnum> hashMaxEnum = Lists.newArrayList(OpenCloseEnum.doubleClose, OpenCloseEnum.onlyRightClose);
        if (hashMaxEnum.contains(this.rangeType)) {
            return true;
        }

        return false;
    }

    /**
     * @throws IllegalStateException
     * @return
     */
    @Deprecated
    public T Max() {
        if (hasMax()) {
            return max;
        } else {
            throw new IllegalStateException("this range has no max value.");
        }

    }

    public T extremeMax() {
        return this.max;
    }

    public T extremeMin() {
        return this.min;
    }

    public boolean contains(T other) {
        if (other.compareTo(this.min) > 0 && this.max.compareTo(other) > 0) {
            return true;
        }

        if (hasMax() && this.max.compareTo(other) == 0) {
            return true;
        }
        if (hasMin() && this.min.compareTo(other) == 0) {
            return true;
        }

        return false;

    }

    /**
     * NOTE  �����Ż������ʹ��ͬһ������������һ�»��������Ǽ�����̱���Ҫ��ֱ�ӣ���˲�û�в���һ�»�������̡�===>����ĳ��󣡣�������ʹ�÷�֧���ܽ��Ŀ�����⡣����ȷ�����Ż����ܱ�֤�Ż�����ȷ�ģ�����������������������ǹؼ����������ǻ����������������������ĳ��
     * ===>2016.12.25 �����Ǽ���ĸ��������Ľڵ㻯��ÿ���ڵ㶼�Ǹ���������ɻ���Ԫ�ػ���
     * 
     * ״̬�ռ�Ļ��ֲ��������ÿ�����Ų�Ҫ���棬�����ͱ�ü�����ˣ�����
     * ==>�����������������߽磩+�߽��������ʽ�ϵı߽磩��������Ժ͸���ģ�ͺͳ���֮������𣩡��������Ҳ�Ǽ�������Ļ�������֮һ!
     * 
     * 
     * �����Ż������¶����ڲ���������ʹ����Щ����֮��Ĺ�ϵ�򵥺���򻯡�����
     * ===>��������࣡������
     * 
     * @param other  
     * @return
     */
    public boolean contains(Range<T> other) {

        T otherExtremeMin = other.extremeMin();
        T otherExtremeMax = other.extremeMax();
        if (this.min.compareTo(otherExtremeMin) < 0) {
            if (this.max.compareTo(otherExtremeMax) > 0) {
                return true;
            }
            if (this.max.compareTo(otherExtremeMax) == 0 && this.hasMax()) {
                return true;
            }
        }
        if (this.min.compareTo(otherExtremeMin) == 0) {
            if (this.hasMin() || !other.hasMin()) {
                if (this.max.compareTo(otherExtremeMax) > 0) {
                    return true;
                } else if (this.max.compareTo(otherExtremeMax) == 0 && this.hasMax()) {
                    return true;
                }
                // ������߼���ԭ����û�и���������еĸ������⣡
                // else (this.max.compareTo(otherExtremeMax) == 0 && !this.hasMax() &&
                // !other.hasMax()){
                // return true;
                // }
            }
        }

        return false;
    }

    /**
     * @throws IllegalArgumentException.
     *             �������䲻�ཻ�������С�
     * @param other
     * @return
     */
    public Range<T> combine(Range<T> other) {

        T max = this.extremeMax().compareTo(other.extremeMax()) >= 0 ? this.extremeMax() : other.extremeMax();
        T min = this.extremeMin().compareTo(other.extremeMin()) <= 0 ? this.extremeMin() : other.extremeMin();
        boolean maxIsAvailable = this.extremeMax().compareTo(other.extremeMax()) >= 0 ? this.hasMax() : other.hasMax();
        boolean minisAvailable = this.extremeMin().compareTo(other.extremeMin()) <= 0 ? this.hasMin() : other.hasMin();

        Range<T> combinedCandidator = new Range<T>(max, min, new OpenCloseEnumCalculator().getOpenCloseEnum(maxIsAvailable, minisAvailable));
        Range<T> remain = combinedCandidator.remain(other);
        if (this.contains(remain)) {
            return combinedCandidator;
        } else {
            throw new IllegalArgumentException();
        }

    }

    /**
     * ���ϼ�������ཻ��������ʱ����������
     * 
     * @throws IllegalArgumentException �������л����ཻ���ǰ�����ϵ��
     * @param other
     * @return
     */
    public Range<T> remain(Range<T> other) {
        Range<T> intersection = this.intersection(other);
        if (intersection == null) {
            return this;
        }
        List<T> extremeValues = Lists.newArrayList();
        extremeValues.add(this.min);
        extremeValues.add(this.max);

        if (!extremeValues.contains(intersection.extremeMax()) && !extremeValues.contains(intersection.extremeMin())) {
            throw new IllegalArgumentException("there is no remain range.");
        }

        T extrememaxOfIntersection = intersection.extremeMax();
        T extrememinOfIntersection = intersection.extremeMin();

        boolean remainHasMax = false;
        boolean remainHasMin = false;
        T extremeMaxOfRemain = null; // not null
        T extremeMinOfRemain = null; // not null
        if (extremeValues.contains(extrememaxOfIntersection)) {
            extremeMaxOfRemain = extrememinOfIntersection;
            extremeMinOfRemain = this.min;
        }
        if (extremeValues.contains(extrememinOfIntersection)) {
            extremeMaxOfRemain = this.max;
            extremeMinOfRemain = extrememaxOfIntersection;
        }
        remainHasMax = this.contains(extremeMaxOfRemain) && !intersection.contains(extremeMaxOfRemain);
        remainHasMin = this.contains(extremeMinOfRemain) && !intersection.contains(extremeMinOfRemain);
        OpenCloseEnum clopseOpenEnum = new OpenCloseEnumCalculator().getOpenCloseEnum(remainHasMax, remainHasMin);

        return new Range<T>(extremeMaxOfRemain, extremeMinOfRemain, clopseOpenEnum);
    }

    /**
     * �������
     * 
     * @param other
     * @return nullable
     */
    public Range<T> intersection(Range<T> other) {
        // T maxOfIntersection = this.extremeMax().compareTo(other.extremeMax()) >= 0 ?
        // other.extremeMax() : this.extremeMax();
        // T minOfIntersection = this.extremeMin().compareTo(other.extremeMin()) <= 0 ?
        // other.extremeMin() : this.extremeMin();
        // if (maxOfIntersection.compareTo(minOfIntersection) < 0) {
        // return null;
        // }
        // if (maxOfIntersection.compareTo(minOfIntersection) == 0 && (this.has)) {
        // return null;
        // }

        // �������������ѵ�ԭ����û�н��������������ã�һ��дһ���룬�����򲢲���һ�����õļ�¼˼ά�ķ�ʽ���Ӷ����¸����ԺͲ��ɿ��Ƶĸо���
        // �����ϵļ�����̺���ʽ�ϵļ����Ӧ��������ӦΪ��ʽ�ϵļ���û�пɱ��ԣ�һ����д����Ĺ����м�������˱仯��һ�ж����ܴ���������
        // ������һ������ģ�ͺ���ʽģ�͵ľ��в�ͬ�Ե�һ��ʵ��������ļ����ǵݹ�ģ����Լ򵥵������ģ���ʽ����ȴ����������������Ҫ�ı亯���Ľӿڣ������Ĵ��ۺܴ󡪡���ʽ�ϵĶ����ǵ�===
        // ����ʽ������ʽ�Ĺ��̣���ʵ�Ǹ����ϵļ���ģ�͵�ÿһ������Ŀ�ʵ���Ե��µġ�����ʽ�ϵļ���ȸ����ϵļ�����ӵ�ϸ�£�
        // ������ֻ��Ҫ��עÿ�������ǿ���ʵ�ֵģ�����ʽ�ϵļ�����Ҫ��ע��������μ����!
        // �Զ���ĸ���һ��Ҫдע�ͣ�ÿ����Ҳ��Ҫдע�͡�
        // ������̣� "���ܵĽ���"ȡ�����Сֵ.
        // �����ֵ������Сֵ�����ڣ�
        // ��...С��...�������ڣ�
        // �����ڣ��Ҵ�������һ���Ǽ����ǿ�ȡ�ģ�����˿հף�����ڣ����������̴Ӹ����Ͻ�ȷʵ�ܹ��򵥵Ĵﵽ����������ʽ�ϣ���Ҫһ���������жϼ���Ŀ�ȡ�ԣ�
        // ����������ϵļ����������ʽ�ϵ��Ż���������򵥵Ĳ����ӿڡ������������Ƿֽⷽ����
        // ��Ҫ���������ϵĲ������������ֵ����Сֵ���ж������ֵ�Ƿ��ȡ��===��ʵ���ϵļ����Ż���==>���㼫��Сֵ���жϼ�ֵ����Դ��===>��ֵ����Դ�����ֿ��ܣ����ʱ�Ͳ���ʱ===>ͳһһ����ʽΪlist������ȡ��ֵ
        // ���Ǹ����ϵĶ�����Զ������ʽ�ϵĶ������ֲ���д�����õĶ��������==����+ϵͳ���ԣ�ʵ�ֵ�������os��network�ȸ����ϵ���������չ�Եȷǹ����Ե�����+��ʽ�ṹ��������

        // û�и����ϵ�֤������ʽ��Զ�Ǵ�ģ�û�������ϵ���������ʽʱ�������ģ���ʽ==����+�����ִ��+��������ռ��㣨û�б仯�ĸ����ϼ��㣩==>�������ϵķ����ͼ��㣬�±ߵļ��㷽���ڸ����Ͼ�������==�������ڼ����ϵ��칹�ԣ�����

        // ����һ��Ҫдע�ͣ���������Ӧ���й�ϵ������Ҫд���������Ĺؼ����ԡ����������һ��Ҫ�򵥣�������

        // Range<T> maxOwner = this.extremeMax().compareTo(other.extremeMax()) >= 0 ? other : this;
        // //max��Сֵ��������1
        // T maxOfIntersection = maxOwner.extremeMax(); //---����2
        // Range<T> minOwner = this.extremeMin().compareTo(other.extremeMin()) <= 0 ? other : this;
        // //min�����ֵ
        // T minOfIntersection = minOwner.extremeMin();
        //
        // if (maxOfIntersection.compareTo(minOfIntersection) < 0) {
        // return null;
        // }
        // if (maxOfIntersection.compareTo(minOfIntersection) == 0 && !(maxOwner.hasMax() ||
        // minOwner.hasMin())) {
        // return null;
        // }


        IntersectionCandidatorCalculator<T> intersectionCandidatorCalculator = new IntersectionCandidatorCalculator<T>(this, other);
        T maxOfIntersection = intersectionCandidatorCalculator.getExtremeMax(); // ---����2
        T minOfIntersection = intersectionCandidatorCalculator.getExtremeMin();

        if (maxOfIntersection.compareTo(minOfIntersection) < 0) {
            return null;
        }
        if (maxOfIntersection.compareTo(minOfIntersection) == 0 && !(intersectionCandidatorCalculator.hasMax() || intersectionCandidatorCalculator.hasMin())) {
            return null;
        }

        OpenCloseEnumCalculator openCloseEnumCalculator = new OpenCloseEnumCalculator();
        Range<T> intersection =
                new Range<T>(maxOfIntersection, minOfIntersection, openCloseEnumCalculator.getOpenCloseEnum(intersectionCandidatorCalculator.hasMax(), intersectionCandidatorCalculator.hasMin()));
        return intersection;
    }

    private class OpenCloseEnumCalculator {
        public OpenCloseEnumCalculator() {}

        public OpenCloseEnum getOpenCloseEnum(boolean maxIsAvailable, boolean minisAvailable) {
            if (maxIsAvailable) {
                if (minisAvailable) {
                    return OpenCloseEnum.doubleClose;
                } else {
                    return OpenCloseEnum.onlyRightClose;
                }
            } else {
                if (minisAvailable) {
                    return OpenCloseEnum.onlyLeftClose;
                } else {
                    return OpenCloseEnum.doubleOpen;
                }
            }
        }
    }

    private class IntersectionCandidatorCalculator<E extends Comparable<? super E>> {
        private Range<E> one;
        private Range<E> two;

        public IntersectionCandidatorCalculator(Range<E> one, Range<E> two) {
            this.one = one;
            this.two = two;
        }

        public E getExtremeMax() {
            return this.getExtremeMaxOwner().get(0).extremeMax();
        }

        public E getExtremeMin() {
            return this.getExtremeMinOwner().get(0).extremeMin();
        }

        public boolean hasMax() {
            List<Range<E>> maxCandicators = this.getExtremeMaxOwner();
            for (Range<E> candidator : maxCandicators) {
                if (candidator.hasMax()) {
                    return true;
                }
            }

            return false;
        }

        public boolean hasMin() {
            List<Range<E>> minCandicators = this.getExtremeMinOwner();
            for (Range<E> candidator : minCandicators) {
                if (candidator.hasMin()) {
                    return true;
                }
            }

            return false;
        }

        /**
         * ����һ��Ҫдע�ͣ���������Ӧ���й�ϵ������Ҫд���������Ĺؼ����ԣ� �������Ӧ���ṩ����������Ҫ���Ķ���ȥ���������ϵ����Բ��������Ĵ��롪����ʹ�õĸø�������ԣ����ʶ���һ��Ҫ������Ӧ�������ܹ�ֱ�ӵĿ������������������һ��Ҫ�򵥣�������
         * �������һЩ"�����߼�"�������Ǵ�Ҷ�֪���ĸ����Ҷ�֪�������Ժ����ԡ������Զ���ĸ���㶼�����ڴ�������ṩ�������ĺ������ʹ�õ������ʺ����ԡ�
         * 
         * ���͸���CandicateExtremeMaxOwner��candidateRange�� ��������ע��; extremeMax: ��range;owner:ӵ�м���ֵ��range.��һ������������������ʱ�伫��ֵ��ȡ�
         * @return 
         */
        private List<Range<E>> getExtremeMaxOwner() {
            List<Range<E>> result = Lists.newArrayList();
            if (one.extremeMax().compareTo(two.extremeMax()) > 0) {
                result.add(two);
            } else if (one.extremeMax().compareTo(two.extremeMax()) == 0) {
                result.add(one);
                result.add(two);
            } else {
                result.add(one);
            }
            return result;
        }

        /**
         * ӵ�м�Сֵ��range.��һ������������������ʱ�伫Сֵ��ȡ�
         * @return
         */
        private List<Range<E>> getExtremeMinOwner() {
            List<Range<E>> result = Lists.newArrayList();
            if (one.extremeMin().compareTo(two.extremeMin()) < 0) {
                result.add(two);
            } else if (one.extremeMin().compareTo(two.extremeMin()) == 0) {
                result.add(one);
                result.add(two);
            } else {
                result.add(one);
            }
            return result;
        }

    }
}
