import pandas as pd
import math
import sys


def rec_CF(watched_list,n):
    """
    :param watched_list:观看过的电影的id
    :param n: 推荐的电影数目
    :return: 推荐的电影id
    """


    data = pd.read_csv('ratings.csv')  #注意这里是引入训练数据的部分，可以之后修改，这个方法只用到了rating的部分


    trainSet={}
    for ele in data.itertuples():
        user, movie, rating = getattr(ele, 'userId'), getattr(ele, 'movieId'), getattr(ele, 'rating')
        trainSet.setdefault(user, {})
        trainSet[user][movie] = rating

    target_user_id=len(trainSet)
    for i in range(len(watched_list)):
        trainSet[target_user_id][watched_list[i]]=5

    user_sim_matrix = {}

    # key = movidID,  value=list of userIDs who have seen this move
    movie_user = {}
    for user, movies in trainSet.items():
        for movie in movies:
            if movie not in movie_user:
                movie_user[movie] = set()
            movie_user[movie].add(user)

    # 下面建立用户相似矩阵
    for movie, users in movie_user.items():     # movid是movieID， users是set集合
        for u in users:           # 对于每个用户， 都得双层遍历
            for v in users:
                if u == v:
                    continue
                user_sim_matrix.setdefault(u, {})      # 把字典的值设置为字典的形式
                user_sim_matrix[u].setdefault(v, 0)
                user_sim_matrix[u][v] += 1     # 这里统计两个用户对同一部电影产生行为的次数， 这个就是余弦相似度的分子


    # 下面计算用户之间的相似性
    for u, related_users in user_sim_matrix.items():
        for v, count in related_users.items():    # 这里面v是相关用户， count是共同对同一部电影打分的次数
            user_sim_matrix[u][v] = count / math.sqrt(len(trainSet[u]) * len(trainSet[v]))   # len 后面的就是用户对电影产生过行为的个数

    k = 20
    aim_user = target_user_id  # 目标用户ID
    rank = {}
    watched_movies = watched_list  # 找出目标用户看到电影
    for v, wuv in sorted(user_sim_matrix[aim_user].items(), key=lambda x: x[1], reverse=True)[0:k]:  # 字典按值从大到小排序， 相关性高到第

        # 把v用户看过的电影推荐给目标用户
        for movie in trainSet[v]:
            if movie in watched_movies:
                continue
            rank.setdefault(movie, 0)
            rank[movie] += wuv
    rec_movies = sorted(rank.items(), key=lambda item: item[1], reverse=True)[:n]
    rec_movies_id=[]
    for k in rec_movies:
        rec_movies_id.append(k[0])
    return rec_movies_id

if __name__ == "__main__":
    watched_list_input=sys.argv[0] #watched_list_input 需要用","隔开
    k=sys.argv[1] #k是topK推荐
    watched_list=watched_list_input.split(',')
    print(rec_CF(watched_list,k))
