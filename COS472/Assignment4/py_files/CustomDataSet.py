import torch
from torch.utils.data import Dataset, DataLoader
import pandas as pd
import numpy as np

class CustomDataSet(Dataset):

    def __init__(self, csv_file, split='train'):

        data = pd.read_csv(csv_file, header=None, skiprows=1)
        categorical_columns = [1, 9] #categorical columns 0- will be 1-hot encoded
        label_column = 13 #target feature 

        #drop the target value, and add back later
        target = data.pop(label_column)

        #1 hot encode categotical data
        one_hot_encoded_data = pd.get_dummies(data[categorical_columns], drop_first=True, dtype=np.int8)
        data = data.drop(columns=categorical_columns)

        data = pd.concat([data, one_hot_encoded_data], axis=1)
        print("Number of Predictor Features", data.shape[1])

        #concat target feature to the end of the df
        data = pd.concat([data, target], axis=1)
        label_column=data.shape[1]
        print("Number of Total Features", data.shape[1])


        #convert all data to float64
        data = data.astype(np.float64)

        # Split data
        total_count = data.shape[0]
        train_end = int(0.8 * total_count)
        valid_end = int(0.9 * total_count)

        self.features = data.drop(columns=[data.columns[-1]]).values
        self.labels = data.iloc[:, -1].values-1

        print("Unique labels:", np.unique(self.labels))

        if split == 'train':
            self.features = self.features[:train_end]
            self.labels = self.labels[:train_end]
        elif split == 'validate':
            self.features = self.features[train_end:valid_end]
            self.labels = self.labels[train_end:valid_end]
        elif split == 'test':
            self.features = self.features[valid_end:]
            self.labels = self.labels[valid_end:]
        else:
            raise ValueError("Split must be 'train', 'validate', or 'test'")

    def __len__(self):
        return len(self.labels)

    def __getitem__(self, idx):
        features = torch.tensor(self.features[idx], dtype=torch.float32)
        label = torch.tensor(self.labels[idx], dtype=torch.long)
        return features, label