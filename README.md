# Text file caching system

By default, the operating system caches data read from disk and written to disk. This implies that the read operations are performed on a system file cache rather than on the physical disk. Similarly, write operations are performed on system cache memory. The caching system that you will implement will simulate the retention of a file's data in memory along with the path to that file on the physical disk.

Cache replacement policy
Cache replacement policies are optimization instructions - or algorithms - that a computer or hardware structure can follow to manage the cache stored on a computer. When the cache is full, the algorithm chooses which elements to discard to make room for new elements.

The variants of the replacement cache policies we will focus on are:

* FIFO
* LRU
* TLRU

## Hit & Miss

To evaluate performance, your cache must communicate various events, such as: hit, miss, put.

Cache hit is a state in which the data requested by the application is cached.

Cache miss is a state in which the data requested by the applicationis NOT cached.

Cache can represent a new state introduced by us, in which the cache receives data to keep (or update) it in its memory.

The given implementation offers a cache hierarchy. It supports the functionality of controlling the size of a cache, removing old items or evaluating performance based on metrics.

## Caches

### ObservableCache
Complete the functionality of the abstract class ObservableCache. The class has:

* a method for setting a cache replacement policy (`setStalePolicy`)
* a method for setting an event listener (`setCacheListener`)
* a method for removing cached entries that are considered "expired" (`clearStaleEntries`)

### ObservableFIFOCache
FIFOCache that represents a non-observable implementation of a queue based cache.

### LRUCache
In the case of LRU cache, the implementation of specific operations (get, put) respect a temporal complexity O(1).

### TimeAwareCache
There are a few similarities between this type of cache and the one from the previous task (LRUCache).

TimeAwareCache has a way to query the timestamp associated with a key (method `getTimeStampOfKey`) and a method to set an expiration policy of the elements (`setExpirePolicy`).

## Observer Pattern
The caches implemented also have a mechanism to signal the events of hit, miss and put (update). The Observer design pattern is applied here, considering cache as the subject and several types of CacheListener as observers.

