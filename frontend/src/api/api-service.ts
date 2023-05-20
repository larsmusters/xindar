import axios, { AxiosInstance } from 'axios'

export interface ApiService {
  get(url: string, params?: any): Promise<any>;
  post(url: string, params?: any): Promise<any>;
  put(url: string, params?: any): Promise<any>;
  delete(url: string): Promise<any>;
}

export class AxiosApiService implements ApiService {
  private axiosInstance: AxiosInstance

  constructor(baseURL: string) {
    this.axiosInstance = axios.create({
      baseURL,
    })
  }

  get(url: string, params?:any): Promise<any> {
    return this.axiosInstance.get(url, { params }).then((response) => response.data)
  }

  post(url: string, data: any, params?:any): Promise<any> {
    return this.axiosInstance.post(url, data, { params } ).then((response) => response.data)
  }

  put(url: string, data: any, params?:any): Promise<any> {
    return this.axiosInstance.put(url, data, { params }).then((response) => response.data)
  }

  delete(url: string): Promise<any> {
    return this.axiosInstance.delete(url).then((response) => response.data)
  }
}