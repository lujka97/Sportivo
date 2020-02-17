using Microsoft.EntityFrameworkCore;
using Sportivo_webAPI.DAL;
using Sportivo_webAPI.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Sportivo_webAPI.Repositories
{
    public class CourtRepository
    {
        public ICollection<Court> GetAll(int id)
        {
            try
            {
                using (var context = new SportivoContext(new DbContextOptions<SportivoContext>()))
                {
                    var courts = context.Courts.Where(court => court.CompanyId == id).ToList();
                    return courts;
                }
            }
            catch { return null; }
        }

        public ICollection<Court> GetAllOfCompanyForSport(int companyId, int sportId)
        {
            try
            {
                using (var context = new SportivoContext(new DbContextOptions<SportivoContext>()))
                {
                    var courts = context.Courts.Where(court => court.CompanyId == companyId && court.SportId == sportId).ToList();
                    return courts;
                }
            }
            catch { return null; }
        }

        public Court Get(int id)
        {
            try
            {
                using (var context = new SportivoContext(new DbContextOptions<SportivoContext>()))
                {
                    var court = context.Courts.FirstOrDefault(c => c.CourtId == id);
                    return court;
                }
            }
            catch { return null; }
        }

        public bool Add(Court court, int userId)
        {
            try
            {
                using (var context = new SportivoContext(new DbContextOptions<SportivoContext>()))
                {
                    var user = context.Users.FirstOrDefault(u => u.UserId == userId);
                    if(user.CompanyId != court.CompanyId)
                    {
                        return false;
                    }

                    context.Courts.Add(court);
                    context.SaveChanges();
                    return true;
                }
            }
            catch { return false; }
        }
        public bool Update(Court court, Court updated)
        {
            try
            {
                using (var context = new SportivoContext(new DbContextOptions<SportivoContext>()))
                {
                    court.CourtName = updated.CourtName;

                    context.SaveChanges();
                    return true;
                }
            }
            catch { return false; }
        }

        public bool Delete(Court court)
        {
            try
            {
                using (var context = new SportivoContext(new DbContextOptions<SportivoContext>()))
                {
                    context.Courts.Remove(court);
                    context.SaveChanges();
                    return true;
                }
            }
            catch { return false; }
        }
    }
}
