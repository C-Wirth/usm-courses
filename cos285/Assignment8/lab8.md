    /**
     * Colby Wirth Lab 8 get method
     * 21 November 2024 
     * Standard get method for MyHashMap
     * @param key the inputted key
     * @return V the value paired with the key
     */
    public V get(K key){
        
        int index = indexFor(key);
        
        //table does contain key -> find the value
        if(table[index] != null)

            for(MyEntry<K,V> entry : table[index]){ 

                if(entry.key.equals(key))
                    return entry.value; 
            }
        else
            return null;
    }