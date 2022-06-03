/**
 * Programmers: Nathan Jaggers, Storm Randolph
 * 
 * CPE 315 - Computer Architecture
 * Dr.Seng
 * Spring 2022
 * 
 */

package Lab6;

import java.lang.Math;

public class cache {

    /****************************************************
     * Class is to organize data and simplify process of
     * simulating a cache in a processor.
     ****************************************************/

    // ---MEMBERS---
    public String name;
    public int sizeTotal;
    public int ways;
    public int sizeBlock;
    public int bitsBlocks;
    public int sizeIndex;
    public int bitsIndex;
    public int searches;
    public int hits;
    public float hitRate;
    public int[][] tagTable;
    public boolean[][] validBits;
    public int[][] lineNumTable; // parallel array to tagTable, table for LRU (least recently used)

    // ---METHODS---
    // constructors
    public cache() {
        // initialize members
        name = "No Name Given";
        sizeTotal = 0;
        ways = 0;
        sizeBlock = 0;
        bitsBlocks = 0;
        sizeIndex = 0;
        bitsIndex = 0;
        searches = 0;
        hits = 0;
        hitRate = 0;
        tagTable = null;
        validBits = null;
        lineNumTable = null;

    }

    public cache(String name, int totalSize, int Associativity, int blockSize) {
        // initialize members
        this.name = name;
        sizeTotal = totalSize;
        ways = Associativity;
        sizeBlock = blockSize;
        bitsBlocks = (int) (Math.log(sizeBlock) / Math.log(2));
        sizeIndex = totalSize / (ways * (blockSize * 4));
        bitsIndex = (int) (Math.log(sizeIndex) / Math.log(2));
        searches = 0;
        hits = 0;
        hitRate = 0;
        tagTable = new int[ways][sizeIndex];
        validBits = new boolean[ways][sizeIndex];
        lineNumTable = new int[ways][sizeIndex];
    }

    // search method
    public void search(int memAddress) {
        // declare varibles
        boolean found;

        // mask out index and offsets
        // math notation
        int byteOffset = memAddress % 4;
        int blockOffset = (memAddress / 4) % sizeBlock;
        int index = ((memAddress / 4) / sizeBlock) % (sizeIndex);
        int tag = ((memAddress / 4) / sizeBlock) / (sizeIndex);

        // go through different ways and see if the address is
        // present in cache at the index
        found = false;
        for (int way = 0; way < ways; way++) {
            if (validBits[way][index] == true) // check valid bit
            {
                if (tag == tagTable[way][index]) {
                    // indicate that address was found
                    found = true;

                    // increment hits
                    hits++;

                    // adjust LRU (Least Recently Used)
                    lineNumTable[way][index] = searches;

                }
            }

        }

        // if address wasn't found at either way, replace empty or LRU with new data
        if (!found) {
            update(index, blockOffset, byteOffset, memAddress, tag);
        }

        // increment amount of searches
        searches++;
    }

    // update method
    public void update(int index, int blockOffset, int byteOffset, int memAddress, int tag) {
        boolean empty;

        empty = false;
        for (int way = 0; way < ways; way++) {
            // check if spot it empty
            if (tagTable[way][index] == 0) {
                // indicate empty is true
                empty = true;

                // fill spot with address
                tagTable[way][index] = tag;

                // flip valid bit to true
                validBits[way][index] = true;

                // update lru table
                lineNumTable[way][index] = searches;

                // leave loop
                break;
            }

        }

        // if there are no empty spots available, replace LRU
        // least recently used
        if (!empty) {
            // getting from line number lru and comparing with currentLowest line number
            int currentLowest = 5000001;
            int lowestTag = 0;
            for (int way = 0; way < ways; way++) {
                if (lineNumTable[way][index] < currentLowest) {
                    lowestTag = tagTable[way][index];
                    currentLowest = lineNumTable[way][index];
                }

            }

            for (int way = 0; way < ways; way++) {
                // check if spot matches LRU
                // adjust stuff here
                if (tagTable[way][index] == lowestTag) {
                    // fill spot with address
                    tagTable[way][index] = tag;
                    // update lru table
                    lineNumTable[way][index] = searches;
                }

            }
        }
    }
    
    // print method
    public void showSummary() {
        // Cache #1
        // Cache size: 2048B Associativity: 1 Block size: 1
        // Hits: 4028929 Hit Rate: 80.58%
        hitRate = ((float) hits / searches) * 100;

        System.out.printf("%s\nCache size: %dB Associativity: %d Block size: %d\n" +
                "Hits: %d Hit Rate: %.2f%%\n", name, sizeTotal, ways, sizeBlock, hits, hitRate);
    }
}
