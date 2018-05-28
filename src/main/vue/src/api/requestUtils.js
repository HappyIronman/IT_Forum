import Vue from 'vue'
import VueResource from 'vue-resource'

Vue.use(VueResource);


export function requestApi(method, path, params, callback) {

  var notification = UIkit.notification({message: '正在加载...', status: 'primary'})
  var baseUrl = global.HOST + '/data/';
  var requestUri = baseUrl + path;
  var responseData = '';
  if (method === 'get') {
    return Vue.http.get(requestUri, {params: params, credentials: true}).then(response => {
      notification.close(true)
      // get body data
      responseData = response.body;
      // console.log(JSON.stringify(response))
      console.log("ResponseData: " + JSON.stringify(responseData));
      return callback(responseData);
    }).catch(response => {
      notification.close(true)
      if (response.status === 401) {
        UIkit.notification({
          message: '<span class="uk-text-small">请先登录哦!&nbsp;&nbsp;</span>' +
          '<a class="uk-text-small uk-text-bold" href="/#/login" target="_blank">点我登录</a>',
          status: 'primary',
          pos: 'top-center',
          timeout: 5000
        });
      } else {
        var msg = response.body.msg ? response.body.msg : '系统异常'
        UIkit.notification({message: '<span uk-icon=\'icon: warning\'></span>' + msg, status: 'danger'})
      }
      // error callback
      console.error("request ERROR! " + JSON.stringify(response));
    });
  }

  else if (method === 'post') {
    return Vue.http.post(requestUri, params, {credentials: true}).then((response) => {
      notification.close(true)
      // success callback
      responseData = response.data;
      console.log("ResponseData: " + JSON.stringify(responseData));
      return callback(responseData);
    }).catch((response) => {
        notification.close(true)
        console.error("request ERROR! " + JSON.stringify(response));
        if (response.status === 401) {
          UIkit.notification({
            message: '<span class="uk-text-small">请先登录哦!&nbsp;&nbsp;</span>' +
            '<a class="uk-text-small uk-text-bold" href="/#/login" target="_blank">点我登录</a>',
            status: 'primary',
            pos: 'top-center',
            timeout: 5000
          });
        } else {
          var msg = response.body.msg ? response.body.msg : '系统异常'
          UIkit.notification({message: '<span uk-icon=\'icon: warning\'></span>' + msg, status: 'danger'})
        }
        // error callback
      }
    );
  }

  else if (method === 'put') {
    return Vue.http.put(requestUri, params, {credentials: true}).then((response) => {
      // success callback
      responseData = response.data;
      console.log("ResponseData: " + responseData);
      return callback(responseData);
    }).catch((response) => {
      console.error("request ERROR! " + JSON.stringify(response));
      // error callback
    });
  }

  else if (method === 'delete') {
    return Vue.http.delete(requestUri, {params: params, credentials: true}).then(response => {
      // get body data
      responseData = response.body;
      console.log("ResponseData: " + responseData);
      return callback(responseData);
    }).catch(response => {
      // error callback
      console.error("request ERROR! " + JSON.stringify(response));
    });
  }

  else {
    console.error("request method not supported! " + method);
    return;
  }

  return responseData
}

