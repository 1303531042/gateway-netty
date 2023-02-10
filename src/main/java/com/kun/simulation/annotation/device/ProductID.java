package com.kun.simulation.annotation.device;

import jdk.internal.org.objectweb.asm.tree.analysis.Value;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ProductID {
    int Value();
}
