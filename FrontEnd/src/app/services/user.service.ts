import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, Subject, of  } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private apiUrl = 'http://localhost:8081'; // Update the API URL
  private tokenKey = 'token';
  private roleChangedSubject = new Subject<void>();

  roleChanged = this.roleChangedSubject.asObservable();

  constructor(private http: HttpClient) {}

  signup(userData: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/user/signup`, userData);
  }

  getUserProfile(): Observable<any> {
    const decodedToken = this.getDecodedToken();
    if (decodedToken && decodedToken.id) {
      const headers = { Authorization: `Bearer ${localStorage.getItem(this.tokenKey)}` };
      return this.http.get<any>(`${this.apiUrl}/brand/profile/${decodedToken.id}`, { headers });
    }
    throw new Error('Decoded token or user ID not found.');
  }
  
  

  getDecodedToken(): { name: string, email: string, role: string, id: number } | null {
    const token = localStorage.getItem(this.tokenKey);
    if (token) {
      try {
        const tokenParts = token.split('.');
        if (tokenParts.length === 3) {
          const decodedToken = JSON.parse(atob(tokenParts[1])) as { name: string, email: string, role: string, id: number};
          return decodedToken;
        }
      } catch (error) {
        console.error('Error decoding token:', error);
      }
    }
    return null;
  }

  updateUserRole(role: string): Observable<any> | undefined {
    const decodedToken = this.getDecodedToken();
    if (decodedToken) {
      decodedToken.role = role;
      this.roleChangedSubject.next();

      // Update the user's role on the backend (if needed)
      return this.http.post(`${this.apiUrl}/user/updateRole`, { role }, {
        headers: { Authorization: `Bearer ${this.getDecodedToken()}` },
      });
    }

    // Handle the case when decodedToken is not present
    console.error('Decoded token not found.');
    return undefined;
  }

  jobPost(postData: { post: string }): Observable<any> {
    const decodedToken = this.getDecodedToken();
    if (decodedToken && decodedToken.email) {
      const headers = { Authorization: `Bearer ${localStorage.getItem(this.tokenKey)}` };
      const requestUrl = `${this.apiUrl}/job/post`;

      return this.http.post<any>(requestUrl, { ...postData, email: decodedToken.email }, { headers });
    }
    throw new Error('Decoded token or user email not found.');
  }

  getBrandProfile(): Observable<any> {
    const decodedToken = this.getDecodedToken();
    if (decodedToken && decodedToken.id) {
      const headers = { Authorization: `Bearer ${localStorage.getItem(this.tokenKey)}` };
      const requestUrl = `${this.apiUrl}/brand/profile/${decodedToken.id}`;

      return this.http.get<any>(requestUrl, { headers });
    }
    throw new Error('Decoded token or user ID not found.');
  }

  getInfluencerProfile(): Observable<any> {
    const decodedToken = this.getDecodedToken();
    if (decodedToken && decodedToken.id) {
      const headers = { Authorization: `Bearer ${localStorage.getItem(this.tokenKey)}` };
      const requestUrl = `${this.apiUrl}/influencer/profile/${decodedToken.id}`;

      return this.http.get<any>(requestUrl, { headers });
    }
    throw new Error('Decoded token or user ID not found.');
  }

  editBrandProfile(profileData: any): Observable<any> {
    const decodedToken = this.getDecodedToken();
    if (decodedToken && decodedToken.id) {
      const headers = { Authorization: `Bearer ${localStorage.getItem(this.tokenKey)}` };
      const requestUrl = `${this.apiUrl}/brand/edit`;

      return this.http.post<any>(requestUrl, { ...profileData, id: decodedToken.id }, { headers });
    }
    throw new Error('Decoded token or user ID not found.');
  }

  editInfluencerProfile(profileData: any): Observable<any> {
    const decodedToken = this.getDecodedToken();
    if (decodedToken && decodedToken.id) {
      const headers = { Authorization: `Bearer ${localStorage.getItem(this.tokenKey)}` };
      const requestUrl = `${this.apiUrl}/influencer/edit`;

      return this.http.post<any>(requestUrl, { ...profileData, id: decodedToken.id }, { headers });
    }
    throw new Error('Decoded token or user ID not found.');
  }

  getBrandPosts(userId: number): Observable<any[]> {
    const headers = { Authorization: `Bearer ${localStorage.getItem(this.tokenKey)}` };
    const requestUrl = `${this.apiUrl}/job/brand/seePost/${userId}`;
    return this.http.get<any[]>(requestUrl, { headers });
  }
  getInfluencerSeePosts(): Observable<any[]> {
    const headers = { Authorization: `Bearer ${localStorage.getItem(this.tokenKey)}` };
    const requestUrl = `${this.apiUrl}/job/influencer/seePost`;

    return this.http.get<any[]>(requestUrl, { headers });
  }
  getAllUserProfiles(): Observable<any[]> {
    const decodedToken = this.getDecodedToken();
    if (decodedToken && decodedToken.id) {
      const headers = { Authorization: `Bearer ${localStorage.getItem(this.tokenKey)}` };
      const requestUrl = `${this.apiUrl}/user/allProfiles/${decodedToken.id}`;

      return this.http.get<any[]>(requestUrl, { headers });
    }
    throw new Error('Decoded token or user ID not found.');
  }

  forgotPassword(email: string): Observable<any> {
    const requestData = { email };
    return this.http.post(`${this.apiUrl}/user/forgotPassword`, requestData);
  }

  

  getUserProfileById(userId: number): Observable<any> {
    const headers = { Authorization: `Bearer ${localStorage.getItem(this.tokenKey)}` };
    const requestUrl = `${this.apiUrl}/user/clickProfile/${userId}`;

    return this.http.get<any>(requestUrl, { headers });
  }

  updateUserHireInfo(post_id: number): Observable<any> {
    const decodedToken = this.getDecodedToken();
    if (decodedToken && decodedToken.id) {
      const userId = decodedToken.id; 
      return this.http.post(`${this.apiUrl}/collab/influencer/hire`, { post_id, id: userId }, {
        headers: { Authorization: `Bearer ${localStorage.getItem(this.tokenKey)}` },
      });
    } else {
      console.error('Decoded token or user ID not found.');
      return of({ error: 'Decoded token or user ID not found.' });
    }
  }

  seeRequestListInfo(post_id: number): Observable<any> {
    const decodedToken = this.getDecodedToken();
    if (decodedToken && decodedToken.id) {
      return this.http.get(`${this.apiUrl}/collab/brand/seePostOffers/${post_id}`, {
        headers: {
          Authorization: `Bearer ${localStorage.getItem(this.tokenKey)}`
        }
      });
    } else {
      console.error('Decoded token or user ID not found.');
      return of({ error: 'Decoded token or user ID not found.' });
    }
  }

  updateOfferStatus(id: number, status: string): Observable<any> {
    const decodedToken = this.getDecodedToken();
    if (decodedToken && decodedToken.id) {
        const userId = decodedToken.id; 
        return this.http.post(`${this.apiUrl}/collab/brand/offerStatusUpdate`, { id, status }, {
            headers: { Authorization: `Bearer ${localStorage.getItem(this.tokenKey)}` },
        });
    } else {
        console.error('Decoded token or user ID not found.');
        return of({ error: 'Decoded token or user ID not found.' });
    }
}



getInfluencerOfferStatus(userId: number): Observable<any[]> {
  const headers = { Authorization: `Bearer ${localStorage.getItem(this.tokenKey)}` };
  const requestUrl = `${this.apiUrl}/collab/influencer/hireStatus/${userId}`;
  return this.http.get<any[]>(requestUrl, { headers });
}


adminAllProfiles(userId: number): Observable<any[]> {
  const headers = { Authorization: `Bearer ${localStorage.getItem(this.tokenKey)}` };
  const requestUrl = `${this.apiUrl}/user/admin/allProfiles/${userId}`;
  return this.http.get<any[]>(requestUrl, { headers });
}

toggleUserStatus(userId: number): Observable<any> {
  return this.http.post(`${this.apiUrl}/user/admin/verifyUser`, { id: userId }, {
    headers: { Authorization: `Bearer ${localStorage.getItem(this.tokenKey)}` },
  });
}

changePassword(oldPassword: string, newPassword: string): Observable<any> {
  const decodedToken = this.getDecodedToken();
  if (decodedToken && decodedToken.email) {
    const headers = { Authorization: `Bearer ${localStorage.getItem(this.tokenKey)}` };
    const requestUrl = `${this.apiUrl}/user/changePassword`;

    return this.http.post<any>(requestUrl, { email: decodedToken.email, oldPassword, newPassword }, { headers });
  }
  throw new Error('Decoded token or user email not found.');
}

deleteUser(userId: number): Observable<any> {
  const headers = { Authorization: `Bearer ${localStorage.getItem(this.tokenKey)}` };
  const requestUrl = `${this.apiUrl}/user/admin/deleteUser`;

  return this.http.post<any>(requestUrl, { id: userId }, { headers });
}


getPaymentDetails(id: number): Observable<any> {
  const requestUrl = `${this.apiUrl}/collab/payment/${id}`;

  return this.http.get<any>(requestUrl, {
    headers: { Authorization: `Bearer ${localStorage.getItem(this.tokenKey)}` },
  });
}


submitPayment(paymentData: any): Observable<any> {
  const requestUrl = `${this.apiUrl}/collab/confirmPayment`;
  return this.http.post<any>(requestUrl, paymentData, {
    headers: { Authorization: `Bearer ${localStorage.getItem(this.tokenKey)}` },
  });
}
}
