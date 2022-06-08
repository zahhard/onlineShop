package data.repository

import javax.inject.Inject

class CommodityRepository @Inject constructor (val localDataSource: LocalDataSource,
                                               val remoteDataSource: RemoteDataSource) {
}