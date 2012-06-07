compressed-generalized-suffix-array
===================================

This is meant to be a memory-efficient generalized suffix array. It's designed to allow search indexing of objects with some String property that is exposed in the interface ObjectWithString. You should probably rename this interface and its method to fit the rest of your code.

This code is provided as is, without any warranty. See LICENSE for the license specifics, but it's just a standard 3 clause BSD license.

Some classes come from colt, a library used for high performance data structures, and are licensed accordingly. I trimmed out unused functions but otherwise it's unaltered. I copied these files into my repository since the amount of code I'm using is relatively small compared to the size of the complete jar.

Pull requests and bug issues are appreciated :)
