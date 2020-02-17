using Microsoft.EntityFrameworkCore;
using Microsoft.IdentityModel.Protocols;
using Microsoft.IdentityModel.Tokens;
using Sportivo_webAPI.DAL;
using Sportivo_webAPI.Models;
using Sportivo_webAPI.Options;
using System;
using System.Collections.Generic;
using System.IdentityModel.Tokens.Jwt;
using System.Linq;
using System.Security.Claims;
using System.Text;
using System.Threading.Tasks;

namespace Sportivo_webAPI.Repositories
{
    public class UserRepository
    {
        public ICollection<User> GetAll(int id)
        {
            try
            {
                using (var context = new SportivoContext(new DbContextOptions<SportivoContext>()))
                {
                    var users = context.Users.ToList();
                    return users;
                }
            }
            catch { return null; }
        }

        public User GetById(int id)
        {
            try
            {
                using (var context = new SportivoContext(new DbContextOptions<SportivoContext>()))
                {
                    var user = context.Users.FirstOrDefault(c => c.UserId == id);
                    return user;
                }
            }
            catch { return null; }
        }

        public User GetByUserName(string username)
        {
            try
            {
                using (var context = new SportivoContext(new DbContextOptions<SportivoContext>()))
                {
                    var user = context.Users.FirstOrDefault(c => c.Username == username);
                    return user;
                }
            }
            catch { return null; }
        }

        public bool Add(User user)
        {
            try
            {
                using (var context = new SportivoContext(new DbContextOptions<SportivoContext>()))
                {
                    context.Users.Add(user);
                    context.SaveChanges();
                    return true;
                }
            }
            catch { return false; }
        }
        public bool Update(User user, User updated)
        {
            try
            {
                using (var context = new SportivoContext(new DbContextOptions<SportivoContext>()))
                {
                    user.FirstName = updated.FirstName;
                    user.LastName = updated.LastName;
                    user.PhoneNumber = updated.PhoneNumber;
                    user.Password = updated.Password;

                    context.SaveChanges();
                    return true;
                }
            }
            catch { return false; }
        }

        public bool Delete(User user)
        {
            try
            {
                using (var context = new SportivoContext(new DbContextOptions<SportivoContext>()))
                {
                    context.Users.Remove(user);
                    context.SaveChanges();
                    return true;
                }
            }
            catch { return false; }
        }


        public User Authenticate(string userName, string password)
        {
            User user;
            using (var context = new SportivoContext(new DbContextOptions<SportivoContext>()))
            {
                user = context.Users.FirstOrDefault(u => u.Username == userName && u.Password == password);
            }
            if (user == null)
                return null;

            // authentication successful, generate jwt token
            var tokenHandler = new JwtSecurityTokenHandler();

            //var jwtSettings = new JwtSettings();
            var key = Encoding.ASCII.GetBytes("secretsecretsecretsecretsecret");
            var tokenDescriptor = new SecurityTokenDescriptor
            {
                Subject = new ClaimsIdentity(new Claim[]
                {
                    new Claim(ClaimTypes.Name, user.UserId.ToString()),
                    new Claim("username", user.Username)
                }),
                Expires = DateTime.UtcNow.AddDays(7),
                SigningCredentials = new SigningCredentials(new SymmetricSecurityKey(key), SecurityAlgorithms.HmacSha256Signature),
            };
            var token = tokenHandler.CreateToken(tokenDescriptor);
            
            user.Token = tokenHandler.WriteToken(token);
            user.Password = null;

            return user;
        }
    }
}
