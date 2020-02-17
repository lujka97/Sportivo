using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Sportivo_webAPI.Models
{
    public enum UserAccessLevel
    {
        Customer,
        Admin
    }
    public class User
    {
        public int UserId { get; set; }
        public string Username { get; set; }
        public ICollection<Reservation> Reservations { get; set; }
        public string FirstName { get; set; }
        public string LastName { get; set; }
        public string PhoneNumber { get; set; }
        public string Password { get; set; }
        public Company Company { get; set; }
        public int? CompanyId { get; set; }
        public string Token { get; set; }
        public UserAccessLevel AccessLevel { get; set; }

        public User()
        {

        }
    }
}
