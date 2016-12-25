package com.range;

import java.util.List;

import com.annotation.Uncompleted;
import com.google.common.collect.Lists;

/**
 * null is undefined.
 * 
 * immutable class————安全性问题，在这里其实是正确性问题——访问控制问题。
 * 
 * @author bl05386
 *
 *         public class Range<T extends Comparable<? super T> , Clonable>
 *         ===>只能说对这些概念理解的不够充分，建模不够、问题空间提的不够！！
 *         
 *         很难一开始就能设计出所有的接口： 你需要的功能不同，接口和对象的属性可能不同。——除了CPU指令系统和开源框架之外！本质上属于计算领域的问题，不属于概念领域的问题。
 *         
 *         由于实现一个接口而再定义一个接口： 说明了一开始就设计出"完整"的接口的难度！！（最要不增加对象的维度——复杂度和间接和解耦的程度增加——都是可以接受的！！！！）。
 *         定义了一个模型后，接口应该能够完整的、一致的表达出目标模型： 比如我们定义了区间且允许开闭，但是从若不定义极点概念，我们就无法从接口上观察出设计者设计的目标模型！
 *
 *  这个类实现起来有些复杂：源于概念上的复杂性：极值概念导致的。
 *  
 *  还有空range的概念的判断：无法判断。概念的定义没有定义好：概念模型上出现了问题！！！
 *  
 *  一个概念在实现过程中发现概念的定义不够，需要扩展和一开始就定义出完整的概念；二者前者更简单但是有风险：可能无法实现（主要是一些边界的问题没有在概念模型中定义好——又回到状态空间的问题上来了！！）！
 *  概念模型的范围是在定义概念的操作是就应该定义下来的（而不是实现！概念关注的是可实现性；实现关注的是实现的好坏问题：比如代码的可维护性等）。
 *  
 * @param <T>
 */

@Uncompleted(description = "immutable is not satisfied.") // 使用时，T必须是不可变的。
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
     * 这个方法已经没有用了,是不良好的接口。因为已经提供了extremeMin方法. 逻辑等价于hasMin+extremeMin.
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
     * NOTE  计算优化：最好使用同一个操作（操作一致化：本质是计算过程本身要简单直接，因此并没有操作一致化这个过程。===>计算的抽象！！）、不使用分支就能解决目标问题。先正确，再优化才能保证优化是正确的！！！！——概念和想象力是关键！！概念是基础能力，想象力是提升的翅膀！
     * ===>2016.12.25 本质是计算的概念化（计算的节点化，每个节点都是概念，概念的组成化和元素化）
     * 
     * 状态空间的划分操作：最好每个部门不要交叉，这样就变得简答明了！！！
     * ==>概念分析（包含概念边界）+边界分析（形式上的边界）（编程特性和概念模型和程序之间的区别）。概念分析也是计算分析的基本工具之一!
     * 
     * 
     * 计算优化：重新定义内部操作并且使得这些操作之间的关系简单和最简化——！
     * ===>概念就是类！！！！
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
                // 多余的逻辑：原因是没有搞清楚过程中的概念问题！
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
     *             ，若区间不相交或者相切。
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
     * 集合减法概念。相交或者相切时，做减法。
     * 
     * @throws IllegalArgumentException 不是相切或者相交而是包含关系。
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
     * 交集概念。
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

        // 在这里遇到困难的原因还是没有将整个计算过程想好，一边写一边想，而程序并不是一种良好的记录思维的方式，从而导致复杂性和不可控制的感觉。
        // 概念上的计算过程和形式上的计算对应不起来：应为形式上的计算没有可变性（一旦在写程序的过程中计算出现了变化，一切都可能打倒重来。）
        // 这又是一个概念模型和形式模型的具有不同性的一个实例：概念的计算是递归的，可以简单的重来的；形式计算却不是这样，重来需要改变函数的接口，重来的代价很大——形式上的东西是的===
        // 从形式到非形式的过程：其实是概念上的计算模型的每一个步骤的可实现性导致的——形式上的计算比概念上的计算更加的细致，
        // 概念上只需要关注每个步骤是可以实现的，而形式上的计算需要关注步骤是如何计算的!
        // 自定义的概念一定要写注释，每个类也需要写注释。
        // 计算过程： "可能的交集"取最大最小值.
        // 若最大值大于最小值，存在；
        // 若...小于...，不存在；
        // 若等于，且存在其中一个是极点是可取的（填充了空白）则存在（这个计算过程从概念上将确实能够简单的达到，但是在形式上，需要一个方法来判断极点的可取性）
        // 对这个概念上的计算过程做形式上的优化（定义出简单的操作接口——根本方法是分解方法）
        // 需要两个概念上的操作：计算最大值和最小值和判断这个极值是否可取的===（实现上的计算优化）==>计算极大极小值；判断极值的来源；===>极值的来源（两种可能：相等时和不等时===>统一一下形式为list）；获取极值
        // 但是概念上的东西永远先于形式上的东西出现才能写出良好的东西。设计==概念+系统特性（实现的依赖如os、network等概念上的依赖；扩展性等非功能性的需求）+形式结构的特征。

        // 没有概念上的证明，形式永远是错的；没噶概念上的引导，形式时不可理解的；形式==概念+概念的执行+概念的最终计算（没有变化的概念上计算）==>根据以上的分析和计算，下边的计算方法在概念上就有问题==都是属于计算上的异构性！！！

        // 概念一定要写注释，并且若与应用有关系，则需要写出这个概念的关键特性。概念的语义一定要简单！！！！

        // Range<T> maxOwner = this.extremeMax().compareTo(other.extremeMax()) >= 0 ? other : this;
        // //max最小值——操作1
        // T maxOfIntersection = maxOwner.extremeMax(); //---操作2
        // Range<T> minOwner = this.extremeMin().compareTo(other.extremeMin()) <= 0 ? other : this;
        // //min的最大值
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
        T maxOfIntersection = intersectionCandidatorCalculator.getExtremeMax(); // ---操作2
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
         * 概念一定要写注释，并且若与应用有关系，则需要写出这个概念的关键特性： 这个特性应该提供出来，而不要让阅读者去分析概念上的特性才能理解你的代码——你使用的该概念的特性（性质定理）一定要在所对应的类上能够直接的看出来！。概念的语义一定要简单！！！！
         * 否则存在一些"特殊逻辑"，除非是大家都知道的概念，大家都知道的属性和特性。对于自定义的概念，你都必须在代码里边提供这个概念的含义和你使用到的性质和特性。
         * 
         * 解释概念CandicateExtremeMaxOwner：candidateRange： 见这个类的注释; extremeMax: 见range;owner:拥有极大值的range.有一个或者两个；有两个时其极大值相等。
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
         * 拥有极小值的range.有一个或者两个；有两个时其极小值相等。
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
