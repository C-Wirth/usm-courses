'''
 * Author: Colby Wirth 
 * Version: 9 November 2024 
 * Course: COS 285 
 * This script provides me with key details regarding 'views' from song_lyrics
 * I use these details for picking view counts for my analysis
'''

import pandas as pd

songs = pd.read_csv('src/song_lyrics.tsv', sep='\t')
print(songs['views'].describe())